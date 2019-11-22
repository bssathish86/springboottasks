package com.springboot.microservices.authenticationservice.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "user_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class User {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "UserSeqGen", sequenceName = "UserSeqGen", initialValue = 100001, allocationSize = 100)
	@GeneratedValue(generator = "UserSeqGen")
	private Long Id;

	@Size(min = 6, max = 10, message = "loginId min 6 and max 10 characters length.")
	@Column(name = "USER_NAME", unique = true, nullable = false, length = 100)
	private String loginId;

	@NotNull(message = "email cannot be missing or empty")
	@Column(name = "EMAIL", unique = false, nullable = false, length = 100)
	private String email;

	@OneToOne(targetEntity = Credential.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(unique = true)
	@JsonIgnoreProperties("user")
	private Credential credential;

	public User() {
		super();
	}

	public User(String loginId, String email) {
		super();
		if (loginId != null)
			this.loginId = loginId.replaceAll("\\s+", "").trim();
		if (email != null)
			this.email = email;
	}

	public User(String loginId, String email, Credential credential) {
		super();
		if (loginId != null)
			this.loginId = loginId.replaceAll("\\s+", "").trim();
		if (email != null)
			this.email = email;
		this.credential = credential;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId.trim();
	}

	public Credential getCredential() {
		return credential;
	}

	public void setCredential(Credential credential) {
		this.credential = credential;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("User [Id=%s, loginId=%s, email=%s, credential=%s]", Id, loginId, email, credential);
	}

}
