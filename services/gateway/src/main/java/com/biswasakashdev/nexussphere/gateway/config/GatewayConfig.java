package com.biswasakashdev.nexussphere.gateway.config;


import com.biswasakashdev.nexussphere.gateway.filters.JwtAuthorizationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final ServiceURLConfig.RegisteredServiceURLs registeredServiceURLs;



    public GatewayConfig(
            JwtAuthorizationFilter jwtAuthorizationFilter,
            ServiceURLConfig.RegisteredServiceURLs registeredServiceURLs
    ) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.registeredServiceURLs = registeredServiceURLs;
    }


    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("auth", (r) -> r
                        .path(
                                "/api/v1/auth/register",
                                "/api/v1/auth"
                        )
                        .uri(registeredServiceURLs.core())
                )
                .route("user-route", r -> r
                        // Routes part of users service where Authorization filter applied.
                        .path(
                                "/api/v1/users/**",
                                "/api/v1/auth/authorization"
                        )
                        .filters(f -> f.filter(jwtAuthorizationFilter))
                        .uri(registeredServiceURLs.core())
                )
                .build();
    }
}
