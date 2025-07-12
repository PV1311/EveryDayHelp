package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> 
	{
		
	}

