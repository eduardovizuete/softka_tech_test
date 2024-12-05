package com.job.micro.personclient.controller;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class ClientControllerTest {

    // REST API Clients testing the status code of getByClientId
    @Test
    void givenClientDoesExists_whenClientInfoIsRetrieved_then200IsReceived()
            throws IOException {
        // Given
        long idClient = 1L;
        HttpGet httpget = new HttpGet("http://localhost:8080/api/clients/" + idClient);

        // When
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            Result result = httpclient.execute(
                    httpget,
                    response -> new Result(response.getCode(), EntityUtils.toString(response.getEntity())));

            // Then
            assertThat(result.status).isEqualTo(200);
        }
    }

    record Result(int status, String content) {
    }

}
