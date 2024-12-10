package com.job.micro.accounttx;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MicroAppAccountTxTest {

	private static final Logger LOG = LoggerFactory.getLogger(MicroAppAccountTxTest.class);

	@Test
	void contextLoads() {
		LOG.info("Running contextLoad test...");
	}

}
