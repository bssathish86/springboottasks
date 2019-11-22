package com.spring.microservices.employeeservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.microservices.employeeservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findRoleByName(String name);

}
