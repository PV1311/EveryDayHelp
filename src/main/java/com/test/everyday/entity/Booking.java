package com.test.everyday.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String serviceType;

    @Column
    private String employeeType;

    @Column
    private String email;

    @Column
    private String userMessage;

    @Column
    private String adminMessage; // nullable by default

    @Column
    private String status = "pending";

    @Column
    private LocalDateTime date;

    
    
    
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(String serviceType, String employeeType, String email, String userMessage,
			String adminMessage, String status, LocalDateTime date) {
		super();
		this.serviceType = serviceType;
		this.employeeType = employeeType;
		this.email = email;
		this.userMessage = userMessage;
		this.adminMessage = adminMessage;
		this.status = status;
		this.date = date;
	}

	
	
	
	public Integer getId() {
		return id;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getAdminMessage() {
		return adminMessage;
	}

	public void setAdminMessage(String adminMessage) {
		this.adminMessage = adminMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}

