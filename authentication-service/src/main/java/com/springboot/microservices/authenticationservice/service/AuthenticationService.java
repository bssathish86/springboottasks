package com.springboot.microservices.authenticationservice.service;

import java.util.List;
import java.util.Optional;

import com.springboot.microservices.authenticationservice.entity.User;

public interface AuthenticationService {

	Optional<User> addUser(User user);

	Optional<User> updateUser(User user);

	Optional<User> findUserById(Long id);

	Optional<User> findUserByloginId(String loginId);

	Optional<User> findUserByGenratedUserId(Long loginId);

	List<User> findAllUser();

	User authenticateUser();

	boolean isExist(User user);

}
