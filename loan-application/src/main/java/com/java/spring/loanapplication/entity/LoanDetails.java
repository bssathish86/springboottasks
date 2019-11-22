package com.java.spring.loanapplication.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoanDetails {

	@Id
	@GeneratedValue
	private Long id;
	private String loanType;
	private BigDecimal loanAmount;
	private BigDecimal loanRate;
	private int Tenure;

	public LoanDetails() {
		super();
	}

	public LoanDetails(String loanType, BigDecimal loanAmount, BigDecimal loanRate, int tenure) {
		super();
		this.loanType = loanType;
		this.loanAmount = loanAmount;
		this.loanRate = loanRate;
		Tenure = tenure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public int getTenure() {
		return Tenure;
	}

	public void setTenure(int tenure) {
		Tenure = tenure;
	}

	@Override
	public String toString() {
		return String.format("LoanDetails [id=%s, loanType=%s, loanAmount=%s, loanRate=%s, Tenure=%s]", id, loanType,
				loanAmount, loanRate, Tenure);
	}

}
