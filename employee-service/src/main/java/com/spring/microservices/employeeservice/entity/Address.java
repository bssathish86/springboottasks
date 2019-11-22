package com.spring.microservices.employeeservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "address_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Address {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "addressSeqGen", sequenceName = "addressSeqGen", initialValue = 100001, allocationSize = 100)
	@GeneratedValue(generator = "addressSeqGen")
	//@JsonProperty("addressID")
	private Long id;

	@Column(name = "STREET_NAME", unique = false, nullable = false, length = 100)
//	@JsonProperty("streetName")
	private String street;

	@Column(name = "CITY_NAME", unique = false, nullable = false, length = 100)
	private String city;

	@Column(name = "STATE_NAME", unique = false, nullable = false, length = 100)
	private String state;

	@Column(name = "PINCODE", unique = false, nullable = false, length = 6)
	private int pincode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	@JsonIgnoreProperties("addresses")
	private Employee employee;

	public Address() {
		super();
	}

	public Address(String street, String city, String state, int pincode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	@JsonIgnore
	public Employee getEmployee() {
		return employee;
	}

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
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (pincode != other.pincode)
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (street == null) {
			if (other.street != null)
				return false;
		} else if (!street.equals(other.street))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Address [id=%s, street=%s, city=%s, state=%s, pincode=%s]", id, street, city, state,
				pincode);
	}
}
