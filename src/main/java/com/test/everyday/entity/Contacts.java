package com.test.everyday.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Contacts")
public class Contacts {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer contactId; // For the same reason as in Employee entity, we make this camel case, otherwise if we delete from all_contacts.html, then from
	//                            there when we send the id as contact_id, then when we create a custom method in repository as deleteByContact_Id, it doesn't
	//                            work and generated error because custom JPA method here has to be deleteByContactId and for that this needs to be camel case.
	//                            But in this case, we don't need to create custom method and can use built in deleteById() JPA method. But still we need to
	//                            make this camel case
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String phone;
	
	@Column
	private String question;
	
	
	
	
	public Contacts() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contacts(String name, String email, String phone, String question) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.question = question;
	}


	
	
	public Integer getContactId() {
		return contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	
	
}
