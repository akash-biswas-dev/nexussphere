package com.biswasakashdev.nexussphere.core.services.impl;

import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.core.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.core.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.core.models.User;
import com.biswasakashdev.nexussphere.core.repository.UsersRepository;
import com.biswasakashdev.nexussphere.core.repository.r2dbc.UsersR2DBCRepository;
import com.biswasakashdev.nexussphere.core.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersR2DBCRepository usersR2dbcRepository;
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> createUser(NewUserRequest newUser) {
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .email(newUser.email())
                .password(passwordEncoder.encode(newUser.password()))
                .firstName(newUser.firstName())
                .lastName(newUser.lastName())
                .gender(newUser.gender())
                .createdOn(LocalDate.now())
                .accountLocked(false)
                .build();

        return userRepository
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
    public Mono<User> findUserById(String userId) {
        Mono<User> usersMono = usersR2dbcRepository
                .findById(userId)
                .switchIfEmpty(Mono.create(sink -> {
                    log.error("User not found with id: {}", userId);
                    sink.error(new UserNotFoundException("User not found"));
                }));
        return verifyUsers(usersMono);
    }

    protected Mono<User> verifyUsers(Mono<User> usersMono) {
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
    public Mono<User> updateUserDetails(String userId, UserProfileRequest profileRequest) {

        return usersR2dbcRepository
                .findById(userId)
                .flatMap((users) -> {
                    // Update username and account status.
                    users.setFirstName(profileRequest.firstName());
                    users.setLastName(profileRequest.lastName());

                    //Add user profile.

                    return usersR2dbcRepository.save(users);
                })
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .onErrorResume(Throwable.class, (err) -> {
                    log.error("User profile setup not successful for user: {}", userId);
                    return Mono.error(new RuntimeException("Service unavailable please try agiain later."));
                });
    }


}
