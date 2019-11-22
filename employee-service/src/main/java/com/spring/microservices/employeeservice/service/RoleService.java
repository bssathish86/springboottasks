package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.employeeservice.entity.Role;

public interface RoleService {

	List<Role> findAll();

	Optional<Role> findRoleById(Long id);

	Optional<Role> findRoleByName(String name);

	Optional<Role> createRole(Role role);

	Optional<Role> updateRoleById(Role role);

	void deleteRoleById(Long id);

	void deleteAllRole();

	boolean isExist(Role role);

}
