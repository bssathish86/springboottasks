package com.example.authenticationconsumer;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = AuthenticationConsumerApplication.class)
class AuthenticationConsumerApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationConsumerApplicationTests.class);

	@Test
	void contextLoads() {

		long loginId = 6801242162341888353l;
		String strPwd = "JSD11hA\\\\;i";

		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("blrproxy.persistent.co.in", 8080));
		requestFactory.setProxy(proxy);
		RestTemplate template = new RestTemplate(requestFactory);

		String url = "https://localhost:8088/users/authenticate?loginId=" + loginId + "&password=" + strPwd;

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> request = new HttpEntity<String>(headers);

		ResponseEntity<String> rs = template.exchange(url, HttpMethod.GET, request, String.class);
		log.info("success" + rs);
	}
}
