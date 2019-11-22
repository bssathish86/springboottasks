package com.java.spring.loanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.spring.loanapplication.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
