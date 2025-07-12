package com.test.everyday.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback 
{
	@Id
	private String email;
	@Column
	private String name;
	@Column
	private String remarks;
	@Column
	private Integer rating;
	

}