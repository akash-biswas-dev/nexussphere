package com.biswasakashdev.nexussphere.core.controller;


import com.biswasakashdev.nexussphere.core.dtos.response.UserResponse;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserProfileRequest;
import com.biswasakashdev.nexussphere.core.dtos.response.UserProfileResponse;
import com.biswasakashdev.nexussphere.core.models.User;
import com.biswasakashdev.nexussphere.core.services.UserService;
import com.biswasakashdev.nexussphere.core.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //    Returns profile if profile completed otherwise return 404.
    @GetMapping
    public Mono<UserResponse> getUser(
            @RequestHeader(name = "Authentication-Info") String userId
    ) {
        Mono<User> usersMono = userService.findUserById(userId);
        return usersMono.map(UsersUtils::getUserResponse);
    }


    @GetMapping("/profile")
    public Mono<UserProfileResponse> getUserProfile(
            @RequestHeader(name = "Authentication-Info") String userId
    ){
        Mono<User> usersMono = userService.findUserById(userId);
        return usersMono.map(UsersUtils::getUserProfileResponse);
    }


    //    Update the profile.
    @PutMapping(value = "/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateProfile(
            @RequestHeader(name = "Authentication-Info") String userId,
            @RequestBody UserProfileRequest userProfileRequest
    ) {
        Mono<User> usersMono = userService.updateUserDetails(userId, userProfileRequest);
        return usersMono.then();
    }

}
