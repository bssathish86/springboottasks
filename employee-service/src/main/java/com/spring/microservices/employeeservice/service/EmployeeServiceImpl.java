package com.spring.microservices.employeeservice.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.entity.Employee;
import com.spring.microservices.employeeservice.entity.Role;
import com.spring.microservices.employeeservice.exception.AddressNotFoundException;
import com.spring.microservices.employeeservice.exception.EmployeeNotFoundException;
import com.spring.microservices.employeeservice.repository.AddressRepository;
import com.spring.microservices.employeeservice.repository.DepartmentRepository;
import com.spring.microservices.employeeservice.repository.EmployeeRepository;
import com.spring.microservices.employeeservice.repository.RoleRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private DepartmentRepository depRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<Employee> findAll() {
		return empRepository.findAll();
	}

	@Override
	public Optional<Employee> findEmployeeById(Long employeeId) {
		return empRepository.findById(employeeId);
	}

	@Override
	public Optional<Employee> findEmployeeByName(String name) {
		return empRepository.findEmployeeByName(name);
	}

	@Override
	public Optional<Employee> findEmployeeAddressById(Long employeeId, Long addressId) {
		// Need to write query for this
		return empRepository.findById(employeeId);
	}

	@Override
	public Optional<Employee> createEmployee(Employee employee) {

		depRepository.save(employee.getDepartment());

		employee.getDepartment().setEmployee(employee);

		Set<Role> roles = setRoles(employee);

		Set<Address> addresses = setAddresses(employee);

		employee.setRoles(roles);

		employee.setAddresses(addresses);

		Employee employeeOptional = empRepository.save(employee);

		return Optional.of(employeeOptional);
	}

	@Override
	public Optional<Employee> updateEmployeeById(Employee employee) {

		depRepository.save(employee.getDepartment());

		employee.getDepartment().setEmployee(employee);

		Set<Role> roles = setRoles(employee);

		Set<Address> addresses = setAddresses(employee);

		employee.setRoles(roles);

		employee.setAddresses(addresses);

		Employee employeeUpdated = empRepository.saveAndFlush(employee);
		return Optional.of(employeeUpdated);
	}

	@Override
	public Optional<Employee> addEmployeeAddress(Long employeeId, Address address) {

		Optional<Employee> employeeDB = empRepository.findById(employeeId);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("id-" + employeeId);

		Employee employeeToUpdate = employeeDB.get();

		employeeToUpdate.getAddresses().add(address);

		address.setEmployee(employeeToUpdate);

		addressRepository.saveAndFlush(address);

		return Optional.of(employeeToUpdate);
	}

	@Override
	public Optional<Employee> updateEmployeeAddressById(Long employeeId, Long addressId, Employee employee) {

		Optional<Employee> employeeDB = empRepository.findById(employeeId);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("id-" + employeeId);

		Optional<Address> addressDB = addressRepository.findById(addressId);

		if (!addressDB.isPresent())
			throw new AddressNotFoundException("id-" + addressId);

		employee.setId(employeeDB.get().getId());

		employee.getAddresses().add(addressDB.get());

		addressDB.get().setEmployee(employee);

		empRepository.saveAndFlush(employee);

		return Optional.of(employee);
	}

	@Override
	public void deleteEmployeeById(Long id) {
		empRepository.deleteById(id);
	}

	@Override
	public void deleteAllEmployees() {
		empRepository.deleteAll();
	}

	@Override
	public void deleteEmployeeAddressById(Long employeeId, Long addressId) {

		Optional<Employee> employeeDB = empRepository.findById(employeeId);

		if (!employeeDB.isPresent())
			throw new EmployeeNotFoundException("id-" + employeeId);

		Optional<Address> addressDB = addressRepository.findById(addressId);

		if (!addressDB.isPresent())
			throw new AddressNotFoundException("id-" + addressId);

		addressRepository.delete(addressDB.get());

		empRepository.delete(employeeDB.get());
	}

	@Override
	public boolean isExist(Employee employee) {
		Optional<Employee> employeeDB = findEmployeeByName(employee.getName());

		if (employeeDB.isPresent())
			return employee.getName().equals(employeeDB.get().getName());

		return false;
	}

	private Set<Role> setRoles(Employee employee) {

		Set<Role> roles = new HashSet<>();

		for (Role role : employee.getRoles()) {
			if (role != null) {
				roleRepository.save(role);
				roles.add(role);
			}
			role.getEmployees().add(employee);
		}
		return roles;
	}

	private Set<Address> setAddresses(Employee employee) {

		Set<Address> addresses = new HashSet<>();

		for (Address address : employee.getAddresses()) {
			if (address != null) {
				addressRepository.save(address);
				addresses.add(address);
			}
			address.setEmployee(employee);
		}
		return addresses;
	}
}
