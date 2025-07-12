package com.test.everyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.everyday.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{
	
	void deleteByEmployeeId(String employee_id);
	
	Employee findByEmployeeId(String employee_id);
	
	Employee findByServiceTypeAndEmployeeType(String serviceType, String employeeType);


}
