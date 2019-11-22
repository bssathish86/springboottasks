package com.spring.microservices.employeeservice;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.entity.Department;
import com.spring.microservices.employeeservice.entity.Employee;
import com.spring.microservices.employeeservice.entity.Role;
import com.spring.microservices.employeeservice.repository.EmployeeRepository;

@SpringBootApplication
public class EmployeeServiceApplication implements CommandLineRunner {

	@Autowired
	private EmployeeRepository empRepository;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Role role = new Role("Admin");
		Role role1 = new Role("User");

		Department dep = new Department("Facilities");

		Address address = new Address("K R garden street", "HAL", "BAngalore", 560017);
		Address address1 = new Address("Pritech park, Ecospace Outer ring road", "Ambalipura village", "BAngalore",
				560103);

		Set<Address> addresses = new HashSet<>();
		addresses.add(address);
		addresses.add(address1);

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		roles.add(role1);

		Employee employee = new Employee("Ramesh", dep, addresses, roles);

		Set<Employee> epmloyeeSet = new HashSet<>();
		epmloyeeSet.add(employee);
		role.setEmployees(epmloyeeSet);

		Set<Employee> epmloyeeSet1 = new HashSet<>();
		epmloyeeSet1.add(employee);
		role1.setEmployees(epmloyeeSet1);

		empRepository.save(employee);
	}

}
