package com.test.everyday.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {
	
	@Id
	private String email;
	
	@Column
	private String password;
	
	

	
	public Admin() {
		super();
	}

	public Admin(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
