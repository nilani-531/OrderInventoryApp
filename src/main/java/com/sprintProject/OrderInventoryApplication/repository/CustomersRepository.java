package com.sprintProject.orderinventoryapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sprintProject.orderinventoryapplication.entity.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer>{
    
	// Custom query method → finds customer by email
    // Returns Optional to handle null safely
	//@Query("SELECT c FROM Customers c WHERE c.emailAddress = :email")
		Optional<Customers> findByEmailAddress(String customerEmailAddress);
	
	
		// Checks if email already exists in database
	//@Query("SELECT COUNT(c) > 0 FROM Customers c WHERE c.emailAddress = :email")
		boolean existsByEmailAddress(String customerEmailAddress);

} 


