package com.job.micro.accounttx.config;

import com.job.micro.accounttx.dto.AccountDTO;
import com.job.micro.accounttx.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    private static final String BASE_URL_API_GATEWAY = "http://api-gateway:9191";

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL_API_GATEWAY)
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(Account.class, AccountDTO.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getClient().getClientId(), AccountDTO::setClientId)
                );

        return modelMapper;
    }

}
