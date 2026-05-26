package com.biswasakashdev.nexussphere.core.controller;


import com.biswasakashdev.nexussphere.common.auth.TokenType;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.core.dtos.response.Authorization;
import com.biswasakashdev.nexussphere.core.dtos.response.SessionDetails;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.services.AuthService;
import com.biswasakashdev.nexussphere.core.services.UserService;
import com.biswasakashdev.nexussphere.core.utils.UsersUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthController {


    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    private static final int SESSION_AGE = 1;

    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registerUser(
            @RequestBody NewUserRequest newUser
    ) {
        return userService
                .createUser(newUser)
                .then();
    }

    /**
     * Generate session when user logged in with email and password.
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SessionDetails> login(
            @RequestBody UserCredentials credentials,
            @RequestParam(name = "rememberMe", required = false, defaultValue = "false") boolean rememberMe
    ) {

//        How many days the generated session token valid.
        Duration duration = rememberMe ? Duration.ofDays(15) : Duration.ofDays(1);

        Mono<Users> usersMono = authService.validateUser(credentials);

        return usersMono
                .flatMap(users -> {


                    String token = jwtService.buildToken(
                            users.getId(),
                            Duration.ofHours(1),
                            TokenType.SESSION,
                            new HashMap<>()
                    );


                    SessionDetails sessionDetails = new SessionDetails(
                            token,
                            duration.toSeconds()
                    );

                    return Mono.just(sessionDetails);
                });
    }

    /**
     * Endpoint to generate the Authorization token to access the resources.
     */

    @GetMapping("/authorization")
    public Mono<ResponseEntity<Authorization>> refreshAuthorization(
            @RequestHeader("Authentication-Info") String userId
    ) {
        Mono<Users> userMono = userService.findUserById(userId);

        return userMono
                .map(user -> {


                    Duration expiration = Duration.ofDays(1);

                    String token = jwtService.buildToken(
                            user.getId(),
                            expiration,
                            TokenType.AUTHORIZATION,
                            new HashMap<>()
                    );

                    Authorization sessionDetails = new Authorization(
                            token,
                            UsersUtils.getUserResponse(user)
                    );

                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body(sessionDetails);
                });
    }
}
