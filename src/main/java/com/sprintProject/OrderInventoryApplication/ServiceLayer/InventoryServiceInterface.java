package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;

public interface InventoryServiceInterface {
	

	    List<Inventory> getAllInventory();

	    Inventory getInventoryById(int inventoryId);

	    Inventory createInventory(Inventory inventory);

	    Inventory updateInventory(int inventoryId, Inventory inventory);

	    void deleteInventory(int inventoryId);

	
}
