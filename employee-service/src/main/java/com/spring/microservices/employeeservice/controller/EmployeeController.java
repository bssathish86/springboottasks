package com.spring.microservices.employeeservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.entity.Employee;
import com.spring.microservices.employeeservice.exception.CustomException;
import com.spring.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.spring.microservices.employeeservice.service.EmployeeService;

@RestController
@Transactional
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> findAll() {

		final List<Employee> employeeList = service.findAll();

		if (employeeList.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@GetMapping("/employees/id/{empId}")
	public Employee findEmployeeById(@PathVariable Long empId) {

		Optional<Employee> employeeDB = service.findEmployeeById(empId);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("empId-" + empId);

		return employeeDB.get();
	}

	@GetMapping("/employees/name/{name}")
	public Employee findEmployeeByName(@PathVariable String name) {

		Optional<Employee> employeeDB = service.findEmployeeByName(name);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("name-" + name);

		return employeeDB.get();
	}

	@PostMapping("/employees")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee) {

		if (service.isExist(employee)) {
			return new ResponseEntity<>(
					new CustomException(
							"Unable to create a employee with name " + employee.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}

		Optional<Employee> employeeDB = service.createEmployee(employee);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(employeeDB.get().getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {

		final Optional<Employee> employeeDB = service.findEmployeeById(id);

		if (!employeeDB.isPresent())
			return ResponseEntity.notFound().build();

		employee.setId(employeeDB.get().getId());

		Employee employeeToUpdate = employeeDB.get();

		employeeToUpdate.setName(employee.getName());
		employeeToUpdate.setAddresses(employee.getAddresses());
		employeeToUpdate.setDepartment(employee.getDepartment());
		employeeToUpdate.setRoles(employee.getRoles());

		service.updateEmployeeById(employeeToUpdate);

		return ResponseEntity.noContent().build();
	}

	@PostMapping("/employees/{employeeId}/address")
	public List<ResponseEntity<Employee>> addEmployeeAddress(@PathVariable Long employeeId,
			@RequestBody Address... address) {

		List<ResponseEntity<Employee>> response = new ArrayList<>();

		List<Address> addresses = Arrays.asList(address);
		URI location;

		for (Address addressTo : addresses) {
			Optional<Employee> employeeDB = service.addEmployeeAddress(employeeId, addressTo);

			location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(employeeDB.get().getId()).toUri();
			response.add(ResponseEntity.created(location).build());
		}

		return response;
	}

	@PutMapping("/employees/{employeeId}/address/{addressId}")
	public ResponseEntity<Employee> updateEmployeeAddress(@PathVariable Long employeeId, @PathVariable Long addressId,
			@Valid @RequestBody Employee employee) {

		Optional<Employee> employeeDB = service.updateEmployeeAddressById(employeeId, addressId, employee);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {

		Optional<Employee> employeeDB = service.findEmployeeById(id);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("id-" + id);
		service.deleteEmployeeById(id);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/employees")
	public ResponseEntity<?> deleteAllEmployee() {

		List<Employee> employeeList = service.findAll();

		if (employeeList == null)
			return new ResponseEntity<>(new CustomException("Unable to delete. employee not found."),
					HttpStatus.NOT_FOUND);

		service.deleteAllEmployees();

		return ResponseEntity.noContent().build();
	}

}
