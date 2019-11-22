package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();

	Optional<Employee> findEmployeeById(Long employeeId);

	Optional<Employee> findEmployeeByName(String name);

	Optional<Employee> findEmployeeAddressById(Long employeeId, Long addressId);

	Optional<Employee> createEmployee(Employee employee);

	Optional<Employee> addEmployeeAddress(Long employeeId, Address address);

	Optional<Employee> updateEmployeeById(Employee employee);

	Optional<Employee> updateEmployeeAddressById(Long employeeId, Long addressId, Employee employee);

	void deleteEmployeeAddressById(Long employeeId, Long addressId);

	void deleteAllEmployees();

	void deleteEmployeeById(Long employeeId);

	boolean isExist(Employee employee);

}
