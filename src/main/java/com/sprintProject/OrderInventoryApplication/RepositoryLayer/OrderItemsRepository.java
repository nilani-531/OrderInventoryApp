package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;

public interface OrderItemsRepository  extends JpaRepository<OrderItems, Integer>{

}
