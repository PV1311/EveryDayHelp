package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.Feedback;


public interface FeedbackRepository extends JpaRepository<Feedback, String>
{
	Feedback findByEmail(String email);
}