package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.UserDetails;

public interface UserRepository extends JpaRepository<UserDetails, String>{
	
	public UserDetails findByEmailAndPassword(String email, String password);
	
	void deleteByEmail(String email);
	
    UserDetails findByEmail(String email); // finding by mail and deleting and other things too as email is primary key here. Returns object of UserDetail
    //                                        which is our entity

}
