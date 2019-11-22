package com.springboot.microservices.authenticationservice.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.microservices.authenticationservice.entity.Credential;
import com.springboot.microservices.authenticationservice.entity.User;
import com.springboot.microservices.authenticationservice.repository.UserRepository;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return null;
	}

	@Override
	public Optional<User> findUserByloginId(String loginId) {
		return userRepository.findUserByloginId(loginId);
	}

	@Override
	public Optional<User> addUser(User user) {

		Credential credential = setCredential(user.getCredential());
		user.setCredential(credential);
		User userDB = userRepository.save(user);

		return Optional.of(userDB);
	}

	@Override
	public Optional<User> updateUser(User user) {
		Credential credential = setCredential(user.getCredential());
		user.setCredential(credential);
		User userDB = userRepository.saveAndFlush(user);
		return Optional.of(userDB);
	}

	@Override
	public User authenticateUser() {
		return null;
	}

	@Override
	public boolean isExist(User user) {

		Optional<User> userDB = findUserByloginId(user.getLoginId());

		if (userDB.isPresent())
			return user.getLoginId().equals(userDB.get().getLoginId());

		return false;
	}

	private Credential setCredential(Credential credential) {

		Credential credentialToSave = new Credential("password");

		Timestamp currentTS = new Timestamp(new Date().getTime());

		if (credential != null) {
			credentialToSave = credential;
		}
		if (credentialToSave.getCreatedate() == null)
			credentialToSave.setCreatedate(currentTS);
		credentialToSave.setUpdateddate(currentTS);

		return credentialToSave;
	}

	@Override
	public Optional<User> findUserByGenratedUserId(Long loginId) {
		return userRepository.findUserByCredential_genratedUserId(loginId);
	}

}
