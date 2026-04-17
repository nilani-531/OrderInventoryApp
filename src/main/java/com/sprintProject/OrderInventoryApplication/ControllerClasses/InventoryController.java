package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	// Get all inventory
	@GetMapping
	public ResponseStructure<List<InventoryResponseDto>> getAllInventory() {
<<<<<<< HEAD
		List<InventoryResponseDto> list = inventoryService.getAllInventory();
        ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Inventory fetched successfully");
        rs.setData(list);
		return rs;
=======
		return inventoryService.getAllInventory();
>>>>>>> master
	}

	// Get inventory by ID
	@GetMapping("/{inventoryId}")
	public ResponseStructure<InventoryResponseDto> getInventoryById(@PathVariable int inventoryId) {
<<<<<<< HEAD
		InventoryResponseDto response = inventoryService.getInventoryById(inventoryId);
        ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Inventory fetched successfully");
        rs.setData(response);
		return rs;
=======
		return inventoryService.getInventoryById(inventoryId);
>>>>>>> master
	}

	// Create new inventory
	@PostMapping
<<<<<<< HEAD
	public ResponseStructure<InventoryResponseDto> createInventory(@org.springframework.web.bind.annotation.RequestParam int storeId, @org.springframework.web.bind.annotation.RequestParam int productId, @RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryService.createInventory(storeId, productId, inventory);
        ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Inventory created successfully");
        rs.setData(response);
		return rs;
=======
	public ResponseStructure<InventoryResponseDto> createInventory(@RequestBody InventoryRequestDto inventory) {
		return inventoryService.createInventory(inventory);
>>>>>>> master
	}

	// Update inventory
	@PutMapping("/{inventoryId}")
<<<<<<< HEAD
	public ResponseStructure<InventoryResponseDto> updateInventory(@PathVariable int inventoryId, @org.springframework.web.bind.annotation.RequestParam int storeId, @org.springframework.web.bind.annotation.RequestParam int productId, @RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryService.updateInventory(inventoryId, storeId, productId, inventory);
        ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Inventory updated successfully");
        rs.setData(response);
		return rs;
=======
	public ResponseStructure<InventoryResponseDto> updateInventory(@PathVariable int inventoryId, @RequestBody InventoryRequestDto inventory) {
		return inventoryService.updateInventory(inventoryId, inventory);
>>>>>>> master
	}

	// Delete inventory
	@DeleteMapping("/{inventoryId}")
	public ResponseStructure<String> deleteInventory(@PathVariable int inventoryId) {
<<<<<<< HEAD
		inventoryService.deleteInventory(inventoryId);
        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Inventory deleted successfully");
        rs.setData("Inventory deleted successfully with id: " + inventoryId);
		return rs;
=======
		return inventoryService.deleteInventory(inventoryId);
>>>>>>> master
	}

}
