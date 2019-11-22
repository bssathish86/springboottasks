package com.java.spring.loanapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue
	private Long id;
	private String officeAddress;
	private String communicationAddress;
	private String residenceAddress;

	public Address() {
		super();
	}

	public Address(String officeAddress, String communicationAddress, String residenceAddress) {
		super();
		this.officeAddress = officeAddress;
		this.communicationAddress = communicationAddress;
		this.residenceAddress = residenceAddress;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getCommunicationAddress() {
		return communicationAddress;
	}

	public void setCommunicationAddress(String communicationAddress) {
		this.communicationAddress = communicationAddress;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	@Override
	public String toString() {
		return String.format("Address [id=%s, officeAddress=%s, communicationAddress=%s, residenceAddress=%s]", id,
				officeAddress, communicationAddress, residenceAddress);
	}

}
