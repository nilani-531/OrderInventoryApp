package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;

public interface OrdersRepository  extends JpaRepository<Orders,Integer>{

}
