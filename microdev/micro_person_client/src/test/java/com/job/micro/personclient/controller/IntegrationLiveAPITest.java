package com.job.micro.personclient.controller;

import com.job.micro.personclient.dto.ClientDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

class IntegrationLiveAPITest {

    static final String BASE_URL = "http://localhost:8080";
    static final String API_CLIENTS = "api/clients/";
    static final String ID = "478758";

    final WebTestClient webClient = WebTestClient.bindToServer()
            .baseUrl(BASE_URL)
            .build();

    @Test
    void givenRunningService_whenDeleteClientById_thenExpectStatus() {
        webClient.delete()
                .uri(API_CLIENTS + ID)
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
