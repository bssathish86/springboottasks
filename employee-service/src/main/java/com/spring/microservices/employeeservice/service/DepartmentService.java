package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import com.spring.microservices.employeeservice.entity.Department;

public interface DepartmentService {

	List<Department> findAll();

	Optional<Department> findDepartmentById(Long id);

	Optional<Department> findDepartmentByName(String name);

	Optional<Department> createDepartment(Department department);

	Optional<Department> updateDepartmentById(Department department);

	void deleteDepartmentById(Long id);

	void deleteAllDepartment();

	boolean isExist(Department department);

}
