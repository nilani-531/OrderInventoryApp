package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer>{

}
