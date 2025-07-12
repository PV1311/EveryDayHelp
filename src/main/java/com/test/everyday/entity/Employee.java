package com.test.everyday.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Employee 
{
	
	@Id
	private String employeeId;
	@Column
	private String name;
	@Column
	private String phone;
	@Column
	private String qualification;
	@Column
	private String experience;
	@Column
	private String gender;
	@Column
	private String employeeType;//drop down
	@Column
	private String serviceType;//LTS STS
	@Column
	private String employeePic;
	@Column
	private String charges;
	@Column
	private String description;//text area
	
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Employee(String employeeId, String name, String phone, String qualification, String experience,
			String gender, String employeeType, String serviceType, String employeePic, String charges,
			String description) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.phone = phone;
		this.qualification = qualification;
		this.experience = experience;
		this.gender = gender;
		this.employeeType = employeeType;
		this.serviceType = serviceType;
		this.employeePic = employeePic;
		this.charges = charges;
		this.description = description;
	}


	public String getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getQualification() {
		return qualification;
	}


	public void setQualification(String qualification) {
		this.qualification = qualification;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getEmployeeType() {
		return employeeType;
	}


	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getEmployeePic() {
		return employeePic;
	}


	public void setEmployeePic(String employeePic) {
		this.employeePic = employeePic;
	}


	public String getCharges() {
		return charges;
	}


	public void setCharges(String charges) {
		this.charges = charges;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	

}
