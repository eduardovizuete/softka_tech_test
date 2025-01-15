package com.job.micro.service_registry;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ServiceRegistryApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ServiceRegistryApplicationTests.class);

	@Test
	void contextLoads() {
		LOG.info("Running contextLoad test...");
	}

}
