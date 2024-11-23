package com.job.micro.personclient.controller;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientIntegrationTest {

    // REST API Clients testing the status code of getByClientId
    @Test
    public void givenClientDoesExists_whenClientInfoIsRetrieved_then200IsReceived()
            throws IOException {

        // Given
        Long idClient = 1L;
        HttpUriRequest request = new HttpGet( "http://localhost:8080/api/clients/" + idClient );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(httpResponse.getCode()).isEqualTo(200);
    }

}
