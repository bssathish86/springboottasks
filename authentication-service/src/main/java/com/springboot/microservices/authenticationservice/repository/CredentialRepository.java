package com.springboot.microservices.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.microservices.authenticationservice.entity.Credential;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

}
