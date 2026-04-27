package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer>{
    
	//@Query("SELECT c FROM Customers c WHERE c.emailAddress = :email")
	
	Optional<Customers> findByEmailAddress(String customerEmailAddress);
	// Custom query method → finds customer by email
    // Returns Optional to handle null safely
	
	//@Query("SELECT COUNT(c) > 0 FROM Customers c WHERE c.emailAddress = :email")
	
	boolean existsByEmailAddress(String customerEmailAddress);
	// Checks if email already exists in database
} 
