package com.biswasakashdev.nexussphere.core.services.impl;

import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.core.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.core.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.core.models.Gender;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.repository.UsersRepository;
import com.biswasakashdev.nexussphere.core.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Users> createUser(NewUserRequest newUser) {

        Users user = Users.builder()
                .email(newUser.email())
                .password(passwordEncoder.encode(newUser.password()))
                .firstName(newUser.firstName())
                .lastName(newUser.lastName())
                .gender(newUser.gender())
                .createdOn(LocalDate.now())
                .accountLocked(false)
                .build();
        return usersRepository
                .saveUser(user)
                .onErrorResume(
                        DuplicateKeyException.class,
                        (err) -> {
                            log.error("Failed to create a user with an existing email: {}", newUser.email());
                            return Mono.error(
                                    new UserAlreadyExistsException(
                                            newUser.email(),
                                            "User already exists"
                                    ));
                        }
                );
    }

    @Override
    public Mono<Users> updateOrSaveUser(Users users) {
        return usersRepository.saveUser(users);
    }

    @Override
    public Mono<Users> findUserByEmailOrUsername(String emailOrUsername) {
        Mono<Users> usersMono = usersRepository
                .findByEmail(emailOrUsername)
                .switchIfEmpty(Mono.create(sink -> {
                    log.error("User not found with Email or Username: {}", emailOrUsername);
                    sink.error(new UserNotFoundException("User not found"));
                }));

        return verifyUsers(usersMono);
    }

    @Override
    public Mono<Users> findUserById(String userId) {
        Mono<Users> usersMono = usersRepository
                .findById(userId)
                .switchIfEmpty(Mono.create(sink -> {
                    log.error("User not found with id: {}", userId);
                    sink.error(new UserNotFoundException("User not found"));
                }));
        return verifyUsers(usersMono);
    }

    protected Mono<Users> verifyUsers(Mono<Users> usersMono) {
        return usersMono
                .flatMap(users -> {
                    if (users.getAccountLocked()) {
                        log.warn("Account locked for user: {}", users.getId());
                        return Mono.error(new AccountLockedException("Account locked, contact to administrator."));
                    }

                    return Mono.just(users);
                });
    }

    @Override
    public Mono<Boolean> isUserExists(String userId) {
        return usersRepository.isUserExistsByEmail(userId);
    }

    @Override
    public Mono<Users> updateUserProfile(String userId, UserProfileRequest profileRequest) {

        return usersRepository
                .findById(userId)
                .flatMap((users) -> {
                    // Update username and account status.
                    users.setFirstName(profileRequest.firstName());
                    users.setLastName(profileRequest.lastName());

                    //Add user profile.

                    return usersRepository.saveUser(users);
                })
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .onErrorResume(Throwable.class, (err) -> {
                    log.error("User profile setup not successful for user: {}", userId);
                    return Mono.error(new RuntimeException("Service unavailable please try agiain later."));
                });
    }

    @Override
    public Mono<Boolean> isUserExistsWithUsername(String username) {
        return usersRepository.isUserExistsByEmail(username);
    }


}
