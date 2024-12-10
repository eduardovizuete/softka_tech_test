package com.job.micro.personclient;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroAppPersonClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(MicroAppPersonClientTest.class);

    @Test
    void contextLoads() {
        LOG.info("Running contextLoad test...");
    }

}
