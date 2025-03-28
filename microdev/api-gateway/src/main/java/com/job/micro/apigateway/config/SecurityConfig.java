package com.job.micro.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers(
                                "/api/persons/**",
                                "/api/clients/**",
                                "/api/accounts/**",
                                "/api/transactions/**",
                                "/api/reports/**").authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(reactiveJwtAuthenticationConverter())));

        return http.build();
    }

    /**
     * Reactive JWT Authentication Converter for Spring Security WebFlux
     * This extracts roles from the "roles" claim in the JWT token.
     */
    @Bean
    public Converter<Jwt, Mono<AbstractAuthenticationToken>> reactiveJwtAuthenticationConverter() {
        return new Converter<>() { // Ensure explicit parameterization
            @Override
            public Mono<AbstractAuthenticationToken> convert(Jwt jwt) {
                JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
                grantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // Extract roles from JWT

                return Mono.just(new JwtAuthenticationToken(jwt, grantedAuthoritiesConverter.convert(jwt)));
            }
        };
    }

}