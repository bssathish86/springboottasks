package com.java.spring.loanapplication.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ProfessionalDetails {

	@Id
	@GeneratedValue
	private Long id;
	private String companyName;
	private String panNumber;
	private Date dateOfJoining;
	private int yearOfExperience;

	public ProfessionalDetails() {
		super();
	}

	public ProfessionalDetails(String companyName, String panNumber, Date dateOfJoining, int yearOfExperience) {
		super();
		this.companyName = companyName;
		this.panNumber = panNumber;
		this.dateOfJoining = dateOfJoining;
		this.yearOfExperience = yearOfExperience;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public int getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	@Override
	public String toString() {
		return String.format(
				"ProfessionalDetails [id=%s, companyName=%s, panNumber=%s, dateOfJoining=%s, yearOfExperience=%s]", id,
				companyName, panNumber, dateOfJoining, yearOfExperience);
	}

}
