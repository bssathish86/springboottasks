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

import com.spring.microservices.employeeservice.entity.Department;
import com.spring.microservices.employeeservice.exception.CustomException;
import com.spring.microservices.employeeservice.exception.DepartmentNotFoundException;
import com.spring.microservices.employeeservice.service.DepartmentService;

@RestController
@Transactional
public class DepartmentController {

	@Autowired
	private DepartmentService service;

	@GetMapping("/departments")
	public List<Department> findAll() {
		return service.findAll();
	}

	@GetMapping("/departments/{id}")
	public Department findDepartmentById(@PathVariable Long id) {

		Optional<Department> departmentDB = service.findDepartmentById(id);

		if (!departmentDB.isPresent())
			throw new DepartmentNotFoundException("id-" + id);

		return departmentDB.get();
	}

	@PostMapping("/departments")
	public ResponseEntity<?> createDepartment(@Valid @RequestBody Department department) {

		if (service.isExist(department)) {
			return new ResponseEntity<>(
					new CustomException(
							"Unable to create a department with name " + department.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}

		Optional<Department> departmentDB = service.createDepartment(department);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(departmentDB.get().getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/departments/{id}")
	public ResponseEntity<Object> updateDepartment(@PathVariable Long id, @Valid @RequestBody Department department) {

		Optional<Department> departmentDB = service.findDepartmentById(id);

		if (!departmentDB.isPresent())
			return ResponseEntity.notFound().build();

		department.setId(departmentDB.get().getId());

		service.updateDepartmentById(department);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/departments/{id}")
	public ResponseEntity<Object> deleteDepartmentById(@PathVariable Long id) {

		Optional<Department> departmentDB = service.findDepartmentById(id);

		if (!departmentDB.isPresent())
			throw new DepartmentNotFoundException("id-" + id);
		service.deleteDepartmentById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/departments")
	public ResponseEntity<Object> deleteAllDepartment() {

		List<Department> departmentList = service.findAll();

		if (departmentList == null)
			return new ResponseEntity<>(new CustomException("Unable to delete. department not found."),
					HttpStatus.NOT_FOUND);

		service.deleteAllDepartment();

		return ResponseEntity.noContent().build();
	}
}
