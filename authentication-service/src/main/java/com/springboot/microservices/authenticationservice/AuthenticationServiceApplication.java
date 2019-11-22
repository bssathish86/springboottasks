package com.springboot.microservices.authenticationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import com.springboot.microservices.authenticationservice.entity.Credential;
import com.springboot.microservices.authenticationservice.entity.User;
import com.springboot.microservices.authenticationservice.service.AuthenticationService;

@SpringBootApplication
public class AuthenticationServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@Autowired
	private AuthenticationService service;

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
		return new EmbeddedTomcatCustomizer();
	}

	private static class EmbeddedTomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

		@Override
		public void customize(TomcatServletWebServerFactory factory) {
			factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
				connector.setAttribute("relaxedPathChars", "<>[\\\\]^`{|}#$!&*@~()");
				connector.setAttribute("relaxedQueryChars", "<>[\\\\]^`{|}#$!&*@~()");
			});
		}
	}

	@Override
	public void run(String... args) throws Exception {

		Credential credential = new Credential("pwd1234");
		service.addUser(new User("Ramesh", "ramesh@gmail.com", credential));
		service.addUser(new User("Makesh", "makesh@gmail.com"));
		service.addUser(new User("Makesh86", "makesh86@gmail.com"));
	}
}
