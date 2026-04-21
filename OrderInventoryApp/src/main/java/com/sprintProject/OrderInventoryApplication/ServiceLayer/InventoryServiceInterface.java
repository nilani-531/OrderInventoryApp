package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;

public interface InventoryServiceInterface {
	

	    // Retrieves all inventory records
	    List<InventoryResponseDto> getAllInventory();

	    // Retrieves an inventory record by its ID
	    InventoryResponseDto getInventoryById(int inventoryId);

	    // Creates a new inventory record for a specific store and product
	    InventoryResponseDto createInventory(int storeId, int productId,InventoryRequestDto inventoryRequestDto);

	    // Updates an existing inventory record
	    InventoryResponseDto updateInventory(int inventoryId, int storeId,
	            int productId ,InventoryRequestDto inventoryRequestDto);

	    // Deletes an inventory record by its ID
	    String deleteInventory(int inventoryId);

		// Provides inventory data to stores
		List<Inventory> getInventoryByStore(int storeId);
	
}
