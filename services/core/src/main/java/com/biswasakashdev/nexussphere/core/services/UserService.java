package com.biswasakashdev.nexussphere.core.services;

import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.core.models.Users;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Users> createUser(NewUserRequest newUserRequest);

    Mono<Users> updateOrSaveUser(Users users);

    Mono<Users> findUserByEmailOrUsername(String emailOrUsername);

    Mono<Users> findUserById(String userId);

    Mono<Boolean> isUserExists(String userId);

    Mono<Users> updateUserProfile(String userId,UserProfileRequest profileRequest);

    Mono<Boolean> isUserExistsWithUsername(String username);

}
