package com.biswasakashdev.nexussphere.core.services.impl;

import com.biswasakashdev.nexussphere.core.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.core.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.core.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.repository.UsersRepository;
import com.biswasakashdev.nexussphere.core.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Mono<Users> validateUser(UserCredentials credentials) {
        Mono<Users> usersMono = usersRepository.findByEmail(credentials.email());

        return usersMono
                .onErrorResume((err) -> {
                    log.error(err.getMessage());
                    return Mono.error(new RuntimeException("Database exception."));
                })
                .flatMap((users) -> {

                    boolean isPasswordMatches = passwordEncoder.matches(credentials.password(), users.getPassword());
                    if (!isPasswordMatches) {
                        log.error("Found invalid password for user: {}", credentials.email());
                        return Mono.error(new InvalidCredentialException("Invalid username or password"));
                    }

                    return Mono.just(users);
                })
                .switchIfEmpty(Mono.error(() -> {
                    log.error("User not exists with credential: {}", credentials.email());
                    return new UserNotFoundException("User doesn't exist.");
                }));
    }

}
