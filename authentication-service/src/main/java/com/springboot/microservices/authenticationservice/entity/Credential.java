package com.springboot.microservices.authenticationservice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name = "Credential_details")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Credential {

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@SequenceGenerator(name = "CredentialSeqGen", sequenceName = "CredentialSeqGen", initialValue = 10001, allocationSize = 100)
	@GeneratedValue(generator = "CredentialSeqGen")
	private Long Id;

	@Column(name = "SYSTEM_GEN_NUM", unique = true, nullable = true, length = 100)
	@JsonProperty("genratedUserId")
	private Long genratedUserId;

	@Size(min = 6, max = 10, message = "password min 6 and max 10 characters length.")
	@Column(name = "PASSWORD", unique = false, nullable = false, length = 100)
	private String password;

	@Column(name = "SYSTEM_GEN_PASSWORD", unique = true, nullable = true, length = 100)
	private String genratedPassword;

	@Column(name = "CREATED_TIME", unique = false, nullable = true, length = 100)
	private Timestamp createdate;

	@Column(name = "UPDATED_TIME", unique = false, nullable = true, length = 100)
	private Timestamp updateddate;

	@OneToOne(mappedBy = "credential", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonIgnoreProperties("credential")
	private User user;

	public Credential() {
		super();
	}

	public Credential(String password) {
		super();
		if (password != null)
			this.password = password.replaceAll("\\s+", "").trim();
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password != null)
			this.password = password.trim();
	}

	public String getGenratedPassword() {
		return genratedPassword;
	}

	public void setGenratedPassword(String genratedPassword) {
		this.genratedPassword = genratedPassword;
	}

	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public Timestamp getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getGenratedUserId() {
		return genratedUserId;
	}

	public void setGenratedUserId(Long genratedUserId) {
		this.genratedUserId = genratedUserId;
	}

	@Override
	public String toString() {
		return String.format(
				"Credential [Id=%s, genratedUserId=%s, password=%s, genratedPassword=%s, createdate=%s, updateddate=%s, user=%s]",
				Id, genratedUserId, password, genratedPassword, createdate, updateddate, user);
	}

}
