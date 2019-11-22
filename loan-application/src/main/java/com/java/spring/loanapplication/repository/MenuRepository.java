package com.java.spring.loanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.spring.loanapplication.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}
