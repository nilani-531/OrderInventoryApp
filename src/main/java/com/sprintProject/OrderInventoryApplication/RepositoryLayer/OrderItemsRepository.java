package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Order_Items;

public interface OrderItemsRepository  extends JpaRepository<Order_Items, Integer>{

}
