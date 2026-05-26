package com.biswasakashdev.nexussphere.gateway.filters;

import com.biswasakashdev.nexussphere.common.auth.TokenType;
import com.biswasakashdev.nexussphere.common.auth.jwt.JwtService;
import com.biswasakashdev.nexussphere.gateway.exceptions.ResourceNotAllowedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements GatewayFilter {

    private final JwtService jwtService;


    private static final List<String> ALLOWED_PATHS_WHEN_TOKEN_TYPE_SESSION = List.of(
            "/api/v1/auth/authorization"
    );

    private static final List<String> FORBIDDEN_PATHS_WHEN_TOKEN_TYPE_AUTHORIZATION = List.of(
            "/api/v1/auth/authorization"
    );


    @NonNull
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            @NonNull GatewayFilterChain chain
    ) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String authorization = request.getHeaders().getFirst("Authorization");

        try {

            if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid authorization found.");
            }
            String token = authorization.substring(7);

            Claims claims = jwtService.extractAllClaims(token);

            String userId = claims.getSubject();

            String tokenType = claims.get("token_type", String.class);

            String path = request.getPath().value();


//            If user token type is SESSION.
            if (Objects.equals(tokenType, TokenType.SESSION.name()) && !isResourceAllowedWhenTokenTypeSession(path)) {
                throw new ResourceNotAllowedException(userId, "Resource not allowed with token type SESSION for user with id: " + userId + " at " + path);
            }

            if (Objects.equals(tokenType, TokenType.AUTHORIZATION.name()) && isResourceAllowedWhenTokenTypeAuthorization(path)) {
                throw  new ResourceNotAllowedException(userId, "Resource not allowed with token type AUTHORIZATION for user with id: " + userId + " at " + path);
            }

            ServerHttpRequest modifiedRequest = request.mutate()
                    .headers((headers) -> headers.set("Authentication-Info", userId)).build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());

        } catch (ResourceNotAllowedException ex) {
            log.error("Resource not allowed for user with id: {} at {} with message: {}", ex.getUserId(), request.getPath(), ex.getMessage());
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().set("WWW-Authenticate", "Resource not allowed.");
            return response.setComplete();
        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Authorization expired for user: {}", expiredJwtException.getClaims().getSubject());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token passed with message: {}", ex.getMessage());
        } catch (Exception ex) {
            log.error("Error occurred while authenticating user with message: {}", ex.getMessage());
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set("WWW-Authenticate", "Invalid authorization found.");
        return response.setComplete();
    }


    boolean isResourceAllowedWhenTokenTypeSession(String path) {
        return ALLOWED_PATHS_WHEN_TOKEN_TYPE_SESSION.contains(path);
    }

    boolean isResourceAllowedWhenTokenTypeAuthorization(String path) {
        return FORBIDDEN_PATHS_WHEN_TOKEN_TYPE_AUTHORIZATION.contains(path);
    }


}
