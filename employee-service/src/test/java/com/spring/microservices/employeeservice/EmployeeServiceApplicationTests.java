package com.spring.microservices.employeeservice;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.microservices.employeeservice.entity.Address;
import com.spring.microservices.employeeservice.entity.Department;
import com.spring.microservices.employeeservice.entity.Employee;
import com.spring.microservices.employeeservice.entity.Role;
import com.spring.microservices.employeeservice.repository.AddressRepository;
import com.spring.microservices.employeeservice.repository.DepartmentRepository;
import com.spring.microservices.employeeservice.repository.EmployeeRepository;
import com.spring.microservices.employeeservice.repository.RoleRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeServiceApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class EmployeeServiceApplicationTests {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private DepartmentRepository departmentRepo;

	@Autowired
	private AddressRepository addressRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Before
	public void createTestData() {

		logger.info("createTestData");

		Role role = new Role("Admin");
		Role role1 = new Role("User");
		Role role2 = new Role("Guest");

		Address address = new Address("K R garden street", "HAL", "BAngalore", 560017);
		Address address1 = new Address("Pritech park, Ecospace Outer ring road", "Ambalipura village", "BAngalore",
				560103);
		Address address2 = new Address("K R garden street", "HAL", "BAngalore", 560017);

		Department department = new Department("Accounts");
		Department department1 = new Department("Sales");

		Set<Address> addresses = new HashSet<>();
		addresses.add(address);
		addresses.add(address1);

		Set<Address> addresses1 = new HashSet<>();
		addresses1.add(address2);

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		roles.add(role1);

		Set<Role> roles1 = new HashSet<>();
		// roles1.add(role2);

		Employee employee = new Employee("Ramesh", department, addresses, roles);
		employeeRepo.save(employee);
		department.setEmployee(employee);

		address.setEmployee(employee);
		address1.setEmployee(employee);
		addressRepo.save(address);
		addressRepo.save(address1);

		role.getEmployees().add(employee);
		role1.getEmployees().add(employee);

		roleRepo.save(role);
		roleRepo.save(role1);
		departmentRepo.save(department);

		Employee employee1 = new Employee("Makesh", department1, addresses1, roles1);
		employeeRepo.save(employee1);
		department.setEmployee(employee1);

		address2.setEmployee(employee1);
		addressRepo.save(address2);
		role2.getEmployees().add(employee1);
		// roleRepo.save(role2);
		departmentRepo.save(department1);
	}

	@Test
	public void test_FindById_Basics() {
		Optional<Employee> employee = employeeRepo.findById(1000001L);
		Assert.assertEquals("Ramesh", employee.get().getName());
	}

	@Test
	public void test_FindById_Wrong() {
		Optional<Employee> employee = employeeRepo.findById(1000002L);
		Assert.assertNotEquals("Ramesh", employee.get().getName());
	}

	@Test
	public void test_updateEmployee() {
		Optional<Employee> employee = employeeRepo.findById(1000001L);
		Assert.assertEquals("Ramesh", employee.get().getName());

		employee.get().setName("Kumar");
		employeeRepo.save(employee.get());

		Optional<Employee> employeeUpdated = employeeRepo.findById(1000001L);
		Assert.assertEquals("Kumar", employeeUpdated.get().getName());
	}

	@Test
	public void test_DeleteEmployee_Success() {
		employeeRepo.deleteById(1000001L);
		Assert.assertEquals(false, employeeRepo.findById(1000001L).isPresent());
	}
}
