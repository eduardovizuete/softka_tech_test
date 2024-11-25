package com.job.micro.personclient.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientTestUtility {

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

}
