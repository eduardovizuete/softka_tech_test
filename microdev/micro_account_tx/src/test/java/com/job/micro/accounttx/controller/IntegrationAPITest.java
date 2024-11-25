package com.job.micro.accounttx.controller;

import com.job.micro.accounttx.dto.ClientDTO;
import com.job.micro.accounttx.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

public class IntegrationAPITest {

    WebTestClient webClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build();

    @Test
    void givenRunningService_whenDeleteClientById_thenExpectStatus() {
        webClient.delete()
                .uri("api/clients/478758")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void givenRunningService_whenGetAllClients_thenExpectStatus() {
        webClient.get()
                .uri("api/clients")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void givenRunningService_whenCreateClient_thenExpectStatus() {
        ClientDTO client = new ClientDTO();
        client.setClientId(478758L);
        client.setPassword("1234");
        client.setStatus("Active");

        client.setName("Jose Lema");
        client.setGender("male");
        client.setAge(25);
        client.setIdentification("1234567890");
        client.setAddress("Otavalo sn y principal");
        client.setTelephone("098254785");

        webClient.post()
                .uri("api/clients")
                .body(Mono.just(client), ClientDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated();
    }

}
