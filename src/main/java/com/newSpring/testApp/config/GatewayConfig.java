package com.newSpring.testApp.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route to book-service for all /api/books/** requests
                .route("book-service", r -> r
                        .path("/api/books/**")
                        .uri("lb://book-service"))

                // Route to book-service for all /api/users/** requests
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .uri("lb://book-service"))

                // Route to book-service for all /api/auth/** requests
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://book-service"))

                .build();
    }
}
