package com.job.micro.accounttx.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@EnableAutoConfiguration
@DataJpaTest
public class ClientTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);

        Client client1 = new Client();
        client1.setClientId(1L);
        client1.setPassword("pass");

        Client client2 = new Client();

        assertThat(client1).isNotEqualTo(client2);

        client2.setClientId(1L);
        client2.setPassword("pass");

        assertThat(client1).isEqualTo(client2);
    }

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