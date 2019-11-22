package com.java.spring.loanapplication.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "user_details")
public class User {

	@Id
	@GeneratedValue
	private Long id;
	private String firstName;
	private String lastName;
	private String fatherName;
	private Date dateOfBirth;
	private String gender;
	private String mobileNumber;
	private String maritalStatus;

	@OneToOne
	private ProfessionalDetails professionalDetails;

	@OneToOne
	private LoanDetails loanDetails;

	@OneToOne
	private Address address;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String fatherName, Date dateOfBirth, String gender,
			String mobileNumber, String maritalStatus, ProfessionalDetails professionalDetails, LoanDetails loanDetails,
			Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.fatherName = fatherName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.maritalStatus = maritalStatus;
		this.professionalDetails = professionalDetails;
		this.loanDetails = loanDetails;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public ProfessionalDetails getProfessionalDetails() {
		return professionalDetails;
	}

	public void setProfessionalDetails(ProfessionalDetails professionalDetails) {
		this.professionalDetails = professionalDetails;
	}

	public LoanDetails getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(LoanDetails loanDetails) {
		this.loanDetails = loanDetails;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return String.format(
				"User [id=%s, firstName=%s, lastName=%s, fatherName=%s, dateOfBirth=%s, gender=%s, mobileNumber=%s, maritalStatus=%s, professionalDetails=%s, loanDetails=%s, address=%s]",
				id, firstName, lastName, fatherName, dateOfBirth, gender, mobileNumber, maritalStatus,
				professionalDetails, loanDetails, address);
	}

}
