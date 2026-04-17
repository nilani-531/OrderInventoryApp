package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;

public interface InventoryServiceInterface {
	

	    ResponseStructure<List<InventoryResponseDto>> getAllInventory();

	    ResponseStructure<InventoryResponseDto> getInventoryById(int inventoryId);

	    ResponseStructure<InventoryResponseDto> createInventory(InventoryRequestDto inventoryRequestDto);

	    ResponseStructure<InventoryResponseDto> updateInventory(int inventoryId, InventoryRequestDto inventoryRequestDto);

	    ResponseStructure<String> deleteInventory(int inventoryId);

	
}
