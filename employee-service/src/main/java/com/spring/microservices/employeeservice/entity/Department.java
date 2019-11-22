package com.spring.microservices.employeeservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "department_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Department {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "depSeqGen", sequenceName = "depSeqGen", initialValue = 10001, allocationSize = 100)
	@GeneratedValue(generator = "depSeqGen")
	// @JsonProperty("departmentID")
	private Long id;

	@Column(name = "NAME", unique = false, nullable = false, length = 100)
	// @JsonProperty("departmentName")
	private String name;

	@OneToOne(mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnoreProperties("department")
	private Employee employee;

	public Department() {
		super();
	}

	public Department(String name) {
		super();
		this.name = name;
	}

	public Department(String name, Employee employee) {
		super();
		this.name = name;
		this.employee = employee;
		this.employee.setDepartment(this);
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
	public Employee getEmployee() {
		return employee;
	}

	@JsonIgnore
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
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
		return String.format("Department [id=%s, name=%s]", id, name);
	}

}
