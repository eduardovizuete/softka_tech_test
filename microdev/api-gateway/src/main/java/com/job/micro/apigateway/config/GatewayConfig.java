package com.job.micro.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private static final String MICROPC_SERVICE = "micropc-service";
    private static final String API_PERSONS = "/api/persons/**";
    private static final String LB_MICROPC_SERVICE = "lb://micropc-service";
    private static final String API_CLIENTS = "/api/clients/**";
    private static final String MICROAT_SERVICE = "microat-service";
    private static final String API_ACCOUNTS = "/api/accounts/**";
    private static final String API_TRANSACTIONS = "/api/transactions/**";
    private static final String LB_MICROAT_SERVICE = "lb://microat-service";
    private static final String API_REPORTS = "/api/reports/**";
    private static final String SECURITY_SERVICE = "security-service";
    private static final String API_AUTH = "/api/auth/**";
    private static final String LB_SECURITY_SERVICE = "lb://security-service";

    private final JwtTokenFilter jwtTokenFilter;

    public GatewayConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(MICROPC_SERVICE, r -> r.path(API_PERSONS)
                        .filters(f -> f.filter(jwtTokenFilter.apply(new JwtTokenFilter.Config())))
                        .uri(LB_MICROPC_SERVICE))
                .route(MICROPC_SERVICE, r -> r.path(API_CLIENTS)
                        .filters(f -> f.filter(jwtTokenFilter.apply(new JwtTokenFilter.Config())))
                        .uri(LB_MICROPC_SERVICE))
                .route(MICROAT_SERVICE, r -> r.path(API_ACCOUNTS)
                        .filters(f -> f.filter(jwtTokenFilter.apply(new JwtTokenFilter.Config())))
                        .uri(LB_MICROAT_SERVICE))
                .route(MICROAT_SERVICE, r -> r.path(API_TRANSACTIONS)
                        .filters(f -> f.filter(jwtTokenFilter.apply(new JwtTokenFilter.Config())))
                        .uri(LB_MICROAT_SERVICE))
                .route(MICROAT_SERVICE, r -> r.path(API_REPORTS)
                        .filters(f -> f.filter(jwtTokenFilter.apply(new JwtTokenFilter.Config())))
                        .uri(LB_MICROAT_SERVICE))
                .route(SECURITY_SERVICE, r -> r.path(API_AUTH)
                        .uri(LB_SECURITY_SERVICE))
                .build();
    }

}
