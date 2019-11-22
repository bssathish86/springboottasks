package com.java.spring.loanapplication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("login");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/fileUpload").setViewName("fileUpload");
		registry.addViewController("/uploadStatus").setViewName("uploadStatus");
		registry.addViewController("/user").setViewName("user");
		registry.addViewController("/success").setViewName("success");
		registry.addViewController("/error").setViewName("error");
		registry.addViewController("/home").setViewName("home");
	}
}
