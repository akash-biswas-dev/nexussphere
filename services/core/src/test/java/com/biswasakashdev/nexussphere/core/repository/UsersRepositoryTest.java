package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.config.AuthConfig;
import com.biswasakashdev.nexussphere.core.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresUserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Import(value = {
        PostgresUserRepositoryImpl.class
})
class UsersRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private UsersRepository usersRepository;

    private final String userEmail = "abc@gmail.com";
    private final Users users = Users.builder()
            .email(userEmail)
            .password("password")
            .firstName("John")
            .lastName("Smith")
            .gender("Male")
            .accountLocked(false)
            .createdOn(LocalDate.now())
            .build();


    @Test
    void shouldSaveUser() {
        usersRepository
                .saveUser(users)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldThrowUserAlreadyExistExceptionWhenTheEmailAlreadyExist() {

        usersRepository
                .saveUser(users)
                .then(usersRepository.saveUser(users))
                .as(StepVerifier::create)
                .expectError(DuplicateKeyException.class)
                .verify();
    }


    @Test
    void shouldReturnEmptyMonoIfUserNotFound() {
        Mono<Users> usersMono = usersRepository.saveUser(users);
        usersMono
                .then(usersRepository.findByEmail(userEmail))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }


}