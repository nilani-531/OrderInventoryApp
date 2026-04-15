package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

}
