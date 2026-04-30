package com.sprintProject.orderinventoryapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sprintProject.orderinventoryapplication.entity.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

	// Find inventory by store ID
	@Query("SELECT i FROM Inventory i WHERE i.stores.storeId = :storeId")
    List<Inventory> findByStoresStoreId(Integer storeId);

	// Find inventory by product ID
	@Query("SELECT i FROM Inventory i WHERE i.products.productId = :productId")
    List<Inventory> findByProductsProductId(Integer productId);

}


