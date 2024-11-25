package com.job.micro.personclient.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigurationPackage
@DataJpaTest
public class ClientTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testEntityAnnotations() {
        // Create a new Client entity
        Client client = new Client();
        client.setClientId(1L);
        client.setPassword("12345");
        client.setStatus("Active");

        client.setName("name");
        client.setIdentification("1234567890");
        client.setAddress("address");
        client.setGender("male");
        client.setTelephone("593 0999");

        // Persist the entity
        entityManager.persist(client);

        //Verify the client is persisted
        Client persistedClient = entityManager.find(Client.class, 1L);
        assertEquals(client.getClientId(), persistedClient.getClientId());
        assertEquals(client.getIdentification(), persistedClient.getIdentification());
    }
}
