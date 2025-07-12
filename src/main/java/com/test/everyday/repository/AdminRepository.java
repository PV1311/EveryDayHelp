package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String>
{
	
	public Admin findByEmailAndPassword(String email,String password);

}
