package com.biswasakashdev.nexussphere.core.controller;

import com.biswasakashdev.nexussphere.common.auth.TokenType;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.core.dtos.requests.NewUserRequest;
import com.biswasakashdev.nexussphere.core.dtos.requests.UserCredentials;
import com.biswasakashdev.nexussphere.core.exception.InvalidCredentialException;
import com.biswasakashdev.nexussphere.core.exception.UserAlreadyExistsException;
import com.biswasakashdev.nexussphere.core.exception.UserNotFoundException;
import com.biswasakashdev.nexussphere.core.models.Gender;
import com.biswasakashdev.nexussphere.core.models.Users;
import com.biswasakashdev.nexussphere.core.services.AuthService;
import com.biswasakashdev.nexussphere.core.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@WebFluxTest(AuthController.class)
class AuthControllerTest {


    @Autowired
    private WebTestClient webClient;


    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldReturn400IfTryCreateAUserWithAnExistingEmail() {
        NewUserRequest request = new NewUserRequest(
                "abc@email.com",
                "password",
                "John",
                "Doe",
                "MALE"
        );

        when(userService.createUser(request))
                .thenReturn(Mono.error(new UserAlreadyExistsException(request.email(),
                        "User already exists.")));

        webClient
                .post()
                .uri("/api/v1/auth/register")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isBadRequest();

    }

    @Test
    void shouldReturn404WhenSendAnInvalidUsername() {

        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        when(authService.validateUser(credentials))
                .thenReturn(Mono.error(new UserNotFoundException("User dont exist.")));

        webClient.post()
                .uri("/api/v1/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void shouldReturn400WhenSendAnInvalidPassword() {
        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        when(authService.validateUser(credentials))
                .thenReturn(Mono.error(new InvalidCredentialException("Invalid credentials.")));

        webClient.post()
                .uri("/api/v1/auth")
                .bodyValue(credentials)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void shouldReturnAuthorizationWhenTryToGenerateWithAuthenticationInfo() {
        UserCredentials credentials = new UserCredentials(
                "abc@email.com",
                "password"
        );

        Users users = Users.builder()
                .id("a-long-userId")
                .firstName("John")
                .email("abc@email.com")
                .lastName("Doe")
                .build();


        when(userService.findUserById(users.getId()))
                .thenReturn(Mono.just(users));

        when(jwtService.buildToken(eq(users.getId()), any(Duration.class), eq(TokenType.AUTHORIZATION), any())).thenReturn("token");


        webClient.get()
                .uri("/api/v1/auth/authorization")
                .header("Authentication-Info", users.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.user.firstName")
                .isEqualTo(users.getFirstName())
                .jsonPath("$.user.lastName")
                .isEqualTo(users.getLastName())
                .jsonPath("$.token")
                .isEqualTo("token");

    }

}