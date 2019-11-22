package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.employeeservice.entity.Role;
import com.spring.microservices.employeeservice.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Override
	public List<Role> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Role> findRoleById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Role> findRoleByName(String name) {
		return repository.findRoleByName(name);
	}

	@Override
	public Optional<Role> createRole(Role role) {
		Role roleOptional = repository.save(role);
		return Optional.of(roleOptional);
	}

	@Override
	public Optional<Role> updateRoleById(Role role) {
		Role roleOptional = repository.saveAndFlush(role);
		return Optional.of(roleOptional);
	}

	@Override
	public void deleteRoleById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteAllRole() {
		repository.deleteAll();
	}

	@Override
	public boolean isExist(Role role) {

		Optional<Role> roleDB = findRoleByName(role.getName());

		if (roleDB.isPresent())
			return role.equals(roleDB.get());

		return false;
	}

}
