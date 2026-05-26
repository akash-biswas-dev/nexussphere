package com.biswasakashdev.nexussphere.core.exception.handlers;


import com.biswasakashdev.nexussphere.common.auth.TokenType;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.common.response.ErrorResponse;
import com.biswasakashdev.nexussphere.core.dtos.response.SessionDetails;
import com.biswasakashdev.nexussphere.core.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.core.exception.ProfileNotCompleteException;
import com.biswasakashdev.nexussphere.core.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.core.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import javax.security.auth.login.AccountLockedException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    private final JwtService jwtService;


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Mono<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return Mono.just(new ErrorResponse(ex.getMessage()));
    }


    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccountLockedException.class)
    public Mono<ErrorResponse> handleAccountLockedException(AccountLockedException ex) {
        return Mono.just(new ErrorResponse(ex.getMessage()));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {
            InvalidCredentialException.class,
            UserAlreadyExistsException.class
    })
    public Mono<ErrorResponse> handleInvalidCredentialsException(RuntimeException ex) {
        return Mono.just(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProfileNotCompleteException.class)
    public Mono<ResponseEntity<SessionDetails>> handleProfileNotCompletedException(ProfileNotCompleteException ex) {

        String token = jwtService.buildToken(
                ex.getUserId(),
                Duration.ofHours(1),
                TokenType.SESSION,
                new HashMap<>()
        );

        SessionDetails sessionDetails = new SessionDetails(
                token,
                Duration.ofHours(1).toSeconds()
        );

        return Mono.just(ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create("/profile"))
                .body(sessionDetails)
        );
    }
}
