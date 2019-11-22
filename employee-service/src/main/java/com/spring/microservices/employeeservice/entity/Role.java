package com.spring.microservices.employeeservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "role_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Role {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "roleSeqGen", sequenceName = "roleSeqGen", initialValue = 50001, allocationSize = 100)
	@GeneratedValue(generator = "roleSeqGen")
	// @JsonProperty("roleID")
	private Long id;

	@Column(name = "NAME", unique = false, nullable = false, length = 100)
	// @JsonProperty("roleName")
	private String name;

	@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties("roles")
	private Set<Employee> employees = new HashSet<>();

	public Role() {
		super();
	}

	public Role(String name) {
		super();
		this.name = name;
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

	@JsonIgnore
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {

		if (this.employees == null) {
			this.employees = employees;
		} else if (this.employees != employees)
			this.employees.clear();
		if (employees != null) {
			this.employees.addAll(employees);
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
		Role other = (Role) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Role [id=%s, name=%s]", id, name);
	}

}
