package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;

public interface InventoryServiceInterface {
	

	    List<InventoryResponseDto> getAllInventory();

	    InventoryResponseDto getInventoryById(int inventoryId);

	    InventoryResponseDto createInventory(int storeId, int productId,InventoryRequestDto inventoryRequestDto)

	    InventoryResponseDto updateInventory(int inventoryId, InventoryRequestDto inventoryRequestDto);

	    String deleteInventory(int inventoryId);

	
}
