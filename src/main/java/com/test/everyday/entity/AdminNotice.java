package com.test.everyday.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

@Entity
//@Setter // We are automatically setting setters with lombok.
//@Getter // We are automatically setting getters with lombok.
@Data // with this we add all the annotations, so for setters, getters, toString, etc, at once.
@NoArgsConstructor //We can either create a default or a parameterized constructor. But if we want both, lombok suggests to make default with this lombok
//annotation here and parameterized manually
public class AdminNotice {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer notice_id;
	
	private String title;
	
	private String description;

}
