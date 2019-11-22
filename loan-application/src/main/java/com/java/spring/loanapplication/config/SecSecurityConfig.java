package com.java.spring.loanapplication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SimpleAuthenticationSuccessHandler successHandler;

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password")).roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("password")).roles("ADMIN");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		/*
		 * http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole(
		 * "ADMIN").antMatchers("/anonymous*") .anonymous().antMatchers("/",
		 * "/home").permitAll().anyRequest().authenticated().and().formLogin()
		 * .loginPage("/login").defaultSuccessUrl("/home",
		 * true).permitAll().and().logout().permitAll();
		 */

		http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login")).and().authorizeRequests()
				.antMatchers("/", "/css/**", "/js/**", "/images/**").permitAll().antMatchers("/user/**")
				.hasAnyRole("USER", "ADMIN").antMatchers("/admin/**").hasRole("ADMIN").anyRequest().authenticated()
				.and().formLogin().loginPage("/").loginProcessingUrl("/login").failureUrl("/?login_error")
				.successHandler(successHandler);

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
