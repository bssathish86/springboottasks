package com.springboot.microservices.authenticationservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.microservices.authenticationservice.utils.AuthenticatorUtils;
import com.springboot.microservices.authenticationservice.utils.EmailProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthenticationServiceApplication.class)
class AuthenticationServiceApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmailProcessor processor;

	@Autowired
	private AuthenticatorUtils utils;

	@Test
	void contextLoads() {

		long loginId = utils.randomNumberGenerator();
		logger.info("Random Number : " + loginId);

		String strPwd = utils.randomPasswordGenerator();
		logger.info("Random Password : " + strPwd);

		processor.sendMail(loginId, "sathishkumar_s@persistent.com", strPwd);
	}
}
