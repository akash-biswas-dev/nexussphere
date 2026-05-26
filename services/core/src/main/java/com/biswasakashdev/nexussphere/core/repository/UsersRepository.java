package com.biswasakashdev.nexussphere.core.repository;

import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.repository.impl.PostgresUserRepositoryImpl;
import lombok.Builder;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


public interface UsersRepository {

    Mono<Users> saveUser(Users users);

    Mono<Users> findByEmail(String emailOrUsername);

    Mono<Users> findById(String userId);

    Mono<Boolean> isUserExistsByEmail(String userId);
/*
    @Builder
    record UserUpdates(
            String username,
            String email,
            String password,
            boolean isAccountLocked,
            boolean isProfileCompleted
    ) {

    }*/

/*
    @Builder
    record UserProfileUpdates(
            String firstName,
            String lastName,
            Gender gender,
            LocalDate dateOfBirth
    ){

    }*/

}
