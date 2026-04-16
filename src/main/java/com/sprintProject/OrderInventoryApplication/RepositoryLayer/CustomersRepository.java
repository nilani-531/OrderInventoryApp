package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer>{
 
	Optional<Customers> findByEmailAddress(String customerEmailAddress);
	
	boolean existsByEmailAddress(String customerEmailAddress);
}
