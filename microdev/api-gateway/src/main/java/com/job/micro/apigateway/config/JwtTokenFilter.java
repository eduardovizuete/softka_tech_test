package com.job.micro.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenFilter extends AbstractGatewayFilterFactory<JwtTokenFilter.Config> {

    public JwtTokenFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // Log the extracted JWT token for debugging
                log.debug("Forwarding JWT token to downstream service: {}", authHeader);

                // Forward the JWT token to the downstream service
                exchange.getRequest().mutate()
                        .header("Authorization", authHeader)
                        .build();
            } else {
                log.debug("No JWT token found in request");
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Configuration properties can be added here if needed
    }

}
