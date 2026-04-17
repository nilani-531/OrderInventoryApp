package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findByStoresStoreId(int storeId);

    List<Inventory> findByProductsProductId(int productId);

}
