package com.example.authenticationconsumer;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AuthenticationConsumerApplication {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationConsumerApplication.class, args);

		connectToService();
	}

	private static void connectToService() {

		log.info("connectToService ");

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
