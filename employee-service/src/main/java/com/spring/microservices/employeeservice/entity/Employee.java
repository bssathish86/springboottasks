package com.spring.microservices.employeeservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "employee_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Employee {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "empSeqGen", sequenceName = "empSeqGen", initialValue = 1000001, allocationSize = 100)
	@GeneratedValue(generator = "empSeqGen")
	// @JsonProperty("employeeID")
	private Long id;

	@Column(name = "NAME", unique = false, nullable = false, length = 100)
	// @JsonProperty("employeeName")
	private String name;

	@OneToOne(targetEntity = Department.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(unique = true)
	@JsonIgnoreProperties("employee")
	private Department department;

	@OneToMany(mappedBy = "employee", targetEntity = Address.class, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("employee")
	private Set<Address> addresses = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "employee_role", joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@JsonIgnoreProperties("employees")
	private Set<Role> roles = new HashSet<>();

	public Employee() {
		super();
	}

	public Employee(String name) {
		super();
		this.name = name;
	}

	public Employee(String name, Department department) {
		super();
		this.name = name;
		this.department = department;
	}

	public Employee(String name, Department department, Set<Address> addresses) {
		super();
		this.name = name;
		this.department = department;
		this.addresses = addresses;
		this.addresses.forEach(address -> address.setEmployee(this));
	}

	public Employee(String name, Department department, Set<Address> addresses, Set<Role> roles) {
		super();
		this.name = name;
		this.department = department;
		this.addresses = addresses;
		this.addresses.forEach(address -> address.setEmployee(this));
		this.roles = roles;
		this.addresses.forEach(role -> role.setEmployee(this));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {

		if (this.addresses == null) {
			this.addresses = addresses;
		} else if (this.addresses != addresses)
			this.addresses.clear();
		if (addresses != null) {
			this.addresses.addAll(addresses);
		}
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {

		if (this.roles == null) {
			this.roles = roles;
		} else if (this.roles != roles)
			this.roles.clear();
		if (roles != null) {
			this.roles.addAll(roles);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Employee [id=%s, name=%s, department=%s, addresses=%s, roles=%s]", id, name, department,
				addresses, roles);
	}

}
