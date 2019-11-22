package com.springboot.microservices.authenticationservice.controller;

import java.net.URI;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.microservices.authenticationservice.entity.Credential;
import com.springboot.microservices.authenticationservice.entity.User;
import com.springboot.microservices.authenticationservice.exception.CustomException;
import com.springboot.microservices.authenticationservice.service.AuthenticationService;
import com.springboot.microservices.authenticationservice.utils.AuthenticatorUtils;
import com.springboot.microservices.authenticationservice.utils.EmailProcessor;

@RestController
@Validated
@Transactional
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;

	@Autowired
	private EmailProcessor processor;

	@Autowired
	private AuthenticatorUtils utils;

	@GetMapping("/users")
	public List<User> findAllUser() {
		return service.findAllUser();
	}

	@PostMapping("/users")
	public ResponseEntity<?> addUser(@Valid @NotNull @NotEmpty @NotBlank @RequestParam("loginId") String loginId,
			@Valid @NotNull @NotEmpty @NotBlank @RequestParam("password") String password,
			@NotNull @NotEmpty @NotBlank @Email(regexp = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}") @RequestParam("email") String email) {

		User user = new User(loginId, email);
		Credential credential = new Credential(password);
		user.setCredential(credential);

		if (service.isExist(user)) {
			return new ResponseEntity<>(
					new CustomException(
							"Unable to create a user with loginId " + user.getLoginId() + " already exist."),
					HttpStatus.CONFLICT);
		}

		Optional<User> userDB = service.addUser(user);

		if (userDB.isPresent()) {
			User userToUpdate = setGeneratedCredential(userDB.get());
			service.updateUser(userToUpdate);
		}
		Credential credentialDB = userDB.get().getCredential();
		processor.sendMail(credentialDB.getGenratedUserId(), user.getEmail(), credentialDB.getGenratedPassword());

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDB.get().getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/users/authenticate")
	public ResponseEntity<?> authenticateUser(@RequestParam("loginId") Long loginId,
			 @RequestParam("password") String password) throws ParseException {

		Optional<User> userDB = service.findUserByGenratedUserId(loginId);

		if (userDB.isPresent()) {
			Credential credentialDB = userDB.get().getCredential();

			if (checkAccountIsValid(loginId, password, credentialDB))
				return new ResponseEntity<>("Credential is valid : " + new Timestamp(new Date().getTime()),
						HttpStatus.OK);
		}
		return new ResponseEntity<>("Credential is not valid ", HttpStatus.UNAUTHORIZED);
	}

	private User setGeneratedCredential(User userToUpdate) {

		long generatedLoginId = utils.randomNumberGenerator();
		String generatedLoginPwd = utils.randomPasswordGenerator();

		Credential credential = userToUpdate.getCredential();
		credential.setGenratedUserId(generatedLoginId);
		credential.setGenratedPassword(generatedLoginPwd);
		return userToUpdate;
	}

	private boolean checkAccountIsValid(Long loginId, String password, Credential credentialDB) throws ParseException {
		if ((credentialDB.getGenratedUserId().equals(loginId)) && credentialDB.getGenratedPassword().equals(password)) {

			Timestamp currentTS = new Timestamp(new Date().getTime());

			float accountTime = utils.calculateTime(currentTS.toString(), credentialDB.getUpdateddate().toString());

			if (Float.compare(accountTime, 2) <= 0)
				return true;
		}
		return false;
	}
}
