package com.springboot.microservices.authenticationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.microservices.authenticationservice.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByloginId(String loginId);

	Optional<User> findUserByCredential_genratedUserId(@Param("loginId") Long loginId);

}
