package com.spring.microservices.employeeservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.employeeservice.entity.Department;
import com.spring.microservices.employeeservice.repository.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository repository;

	@Override
	public List<Department> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Department> findDepartmentById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Department> findDepartmentByName(String name) {
		return repository.findDepartmentByName(name);
	}

	@Override
	public Optional<Department> createDepartment(Department department) {
		Department departmentOptional = repository.save(department);
		return Optional.of(departmentOptional);
	}

	@Override
	public Optional<Department> updateDepartmentById(Department department) {
		Department departmentOptional = repository.save(department);
		return Optional.of(departmentOptional);
	}

	@Override
	public void deleteDepartmentById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteAllDepartment() {
		repository.deleteAll();
	}

	@Override
	public boolean isExist(Department department) {
		Optional<Department> departmentDB = findDepartmentByName(department.getName());

		if (departmentDB.isPresent())
			return department.equals(departmentDB.get());

		return false;
	}
}
