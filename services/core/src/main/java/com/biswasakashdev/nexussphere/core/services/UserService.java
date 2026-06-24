package com.biswasakashdev.nexussphere.core.services;

import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.core.models.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> createUser(NewUserRequest newUserRequest);

    Mono<User> findUserById(String userId);

    Mono<User> updateUserDetails(String userId, UserProfileRequest profileRequest);


}
