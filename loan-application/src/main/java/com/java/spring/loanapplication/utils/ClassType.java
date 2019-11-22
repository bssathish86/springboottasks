package com.java.spring.loanapplication.utils;

public enum ClassType {

	ADDRESS("Address"), LOAN_DETAILS("LoanDetails"), PROFESSIONAL_DETAILS("ProfessionalDetails");

	private String value;

	public String getValue() {
		return this.value;
	}

	ClassType(String value) {
		this.value = value;
	}
}
