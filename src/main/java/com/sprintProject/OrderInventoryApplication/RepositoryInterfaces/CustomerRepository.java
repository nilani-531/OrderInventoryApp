package com.sprintProject.OrderInventoryApplication.RepositoryInterfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
