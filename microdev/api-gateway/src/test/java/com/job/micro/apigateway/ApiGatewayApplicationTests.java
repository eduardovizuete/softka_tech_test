package com.job.micro.apigateway;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiGatewayApplicationTests {

	private static final Logger LOG = LoggerFactory.getLogger(ApiGatewayApplicationTests.class);

	@Test
	void contextLoads() {
		LOG.info("Running contextLoad test...");
	}

}
