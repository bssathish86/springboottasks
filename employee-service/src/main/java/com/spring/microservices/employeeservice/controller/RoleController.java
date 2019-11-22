package com.spring.microservices.employeeservice.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.microservices.employeeservice.entity.Role;
import com.spring.microservices.employeeservice.exception.CustomException;
import com.spring.microservices.employeeservice.exception.RoleNotFoundException;
import com.spring.microservices.employeeservice.service.RoleService;

@RestController
@Transactional
public class RoleController {

	@Autowired
	private RoleService service;

	@GetMapping("/roles")
	public List<Role> findAll() {
		return service.findAll();
	}

	@GetMapping("/roles/{id}")
	public Role findEmployeeById(@PathVariable Long id) {

		Optional<Role> roleDB = service.findRoleById(id);

		if (!roleDB.isPresent())
			throw new RoleNotFoundException("id-" + id);

		return roleDB.get();
	}

	@PostMapping("/roles")
	public ResponseEntity<?> createRole(@Valid @RequestBody Role role) {

		if (service.isExist(role)) {
			return new ResponseEntity<>(
					new CustomException("Unable to create a role with name " + role.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}

		Optional<Role> roleDB = service.createRole(role);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(roleDB.get().getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/roles/{id}")
	public ResponseEntity<Object> updateRole(@PathVariable Long id, @Valid @RequestBody Role role) {

		Optional<Role> roleDB = service.findRoleById(id);

		if (!roleDB.isPresent())
			return ResponseEntity.notFound().build();

		Role roleToUpdate = roleDB.get();

		roleToUpdate.setName(role.getName());
		roleToUpdate.setEmployees(role.getEmployees());

		service.updateRoleById(roleToUpdate);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<Object> deleteRole(@PathVariable Long id) {

		Optional<Role> roleDB = service.findRoleById(id);

		if (!roleDB.isPresent())
			throw new RoleNotFoundException("id-" + id);

		service.deleteRoleById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/roles")
	public ResponseEntity<?> deleteAllRole() {

		List<Role> roleList = service.findAll();

		if (roleList == null)
			return new ResponseEntity<>(new CustomException("Unable to delete. role not found."), HttpStatus.NOT_FOUND);

		service.deleteAllRole();

		return ResponseEntity.noContent().build();
	}

}
