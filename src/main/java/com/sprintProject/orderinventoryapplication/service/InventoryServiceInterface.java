package com.sprintProject.orderinventoryapplication.service;

import java.util.List;

import com.sprintProject.orderinventoryapplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.InventoryResponseDto;

public interface InventoryServiceInterface {

	// Retrieves all inventory records
	List<InventoryResponseDto> getAllInventory();

	// Retrieves an inventory record by its ID
	InventoryResponseDto getInventoryById(Integer inventoryId);

	// Creates a new inventory record for a specific store and product
	InventoryResponseDto createInventory(Integer storeId, Integer productId, InventoryRequestDto inventoryRequestDto);

	// Updates an existing inventory record
	InventoryResponseDto updateInventory(Integer inventoryId, Integer storeId, Integer productId,
			InventoryRequestDto inventoryRequestDto);

	// Deletes an inventory record by its ID
	String deleteInventory(Integer inventoryId);

	// Retrieves inventory records for a specific store
	List<InventoryResponseDto> getInventoryByStore(Integer storeId);

	// Retrieves inventory records for a specific product
	List<InventoryResponseDto> getInventoryByProduct(Integer productId);

}


