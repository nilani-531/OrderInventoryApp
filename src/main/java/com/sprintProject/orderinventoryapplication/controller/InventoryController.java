package com.sprintProject.orderinventoryapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.orderinventoryapplication.service.InventoryServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200/")
public class InventoryController {

	@Autowired
	private InventoryServiceInterface inventoryServiceInterface;

	// Get all inventory
	@GetMapping
	public ResponseStructure<List<InventoryResponseDto>> getAllInventory() {
		List<InventoryResponseDto> list = inventoryServiceInterface.getAllInventory();
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(list);
		return rs;

	}

	// Get inventory by ID
	@GetMapping("/{inventoryId}")
	public ResponseStructure<InventoryResponseDto> getInventoryById(@PathVariable Integer inventoryId) {
		InventoryResponseDto response = inventoryServiceInterface.getInventoryById(inventoryId);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(response);
		return rs;
	}

	// Create new inventory
	@PostMapping
	public ResponseStructure<InventoryResponseDto> createInventory(
			@org.springframework.web.bind.annotation.RequestParam Integer storeId,
			@org.springframework.web.bind.annotation.RequestParam Integer productId,@Valid
			@RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryServiceInterface.createInventory(storeId, productId, inventory);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(201);
		rs.setMsg("Inventory created successfully");
		rs.setData(response);
		return rs;
	}

	// Update inventory
	@PutMapping("/{inventoryId}")
	public ResponseStructure<InventoryResponseDto> updateInventory(@PathVariable Integer inventoryId,
			@org.springframework.web.bind.annotation.RequestParam Integer storeId,
			@org.springframework.web.bind.annotation.RequestParam Integer productId,@Valid
			@RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryServiceInterface.updateInventory(inventoryId, storeId, productId, inventory);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory updated successfully");
		rs.setData(response);
		return rs;
	}

	// Delete inventory
	@DeleteMapping("/{inventoryId}")
	public ResponseStructure<String> deleteInventory(@PathVariable Integer inventoryId) {
		inventoryServiceInterface.deleteInventory(inventoryId);
		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory deleted successfully");
		rs.setData("Inventory deleted successfully with id: " + inventoryId);
		return rs;
	}

	// Get inventory by store ID
	@GetMapping("/store/{storeId}")
	public ResponseStructure<List<InventoryResponseDto>> getInventoryByStore(@PathVariable Integer storeId) {
		List<InventoryResponseDto> list = inventoryServiceInterface.getInventoryByStore(storeId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched by store");
		rs.setData(list);
		return rs;
	}

	// Get inventory by product ID
	@GetMapping("/product/{productId}")
	public ResponseStructure<List<InventoryResponseDto>> getInventoryByProduct(@PathVariable Integer productId) {
		List<InventoryResponseDto> list = inventoryServiceInterface.getInventoryByProduct(productId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched by product");
		rs.setData(list);
		return rs;
	}

}


