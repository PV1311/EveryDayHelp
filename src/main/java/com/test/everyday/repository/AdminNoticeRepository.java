package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.AdminNotice;

public interface AdminNoticeRepository extends JpaRepository<AdminNotice, Integer> {

}
