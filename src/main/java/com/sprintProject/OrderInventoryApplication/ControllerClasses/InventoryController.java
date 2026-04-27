package com.sprintProject.OrderInventoryApplication.ControllerClasses;

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

import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.InventoryService;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "http://localhost:4200/")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	// Get all inventory
	@GetMapping
	public ResponseStructure<List<InventoryResponseDto>> getAllInventory() {
		List<InventoryResponseDto> list = inventoryService.getAllInventory();
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(list);
		return rs;

	}

	// Get inventory by ID
	@GetMapping("/{inventoryId}")
	public ResponseStructure<InventoryResponseDto> getInventoryById(@PathVariable int inventoryId) {
		InventoryResponseDto response = inventoryService.getInventoryById(inventoryId);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(response);
		return rs;
	}

	// Create new inventory
	@PostMapping
	public ResponseStructure<InventoryResponseDto> createInventory(
			@org.springframework.web.bind.annotation.RequestParam int storeId,
			@org.springframework.web.bind.annotation.RequestParam int productId,
			@RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryService.createInventory(storeId, productId, inventory);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(201);
		rs.setMsg("Inventory created successfully");
		rs.setData(response);
		return rs;
	}

	// Update inventory
	@PutMapping("/{inventoryId}")
	public ResponseStructure<InventoryResponseDto> updateInventory(@PathVariable int inventoryId,
			@org.springframework.web.bind.annotation.RequestParam int storeId,
			@org.springframework.web.bind.annotation.RequestParam int productId,
			@RequestBody InventoryRequestDto inventory) {
		InventoryResponseDto response = inventoryService.updateInventory(inventoryId, storeId, productId, inventory);
		ResponseStructure<InventoryResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory updated successfully");
		rs.setData(response);
		return rs;
	}

	// Delete inventory
	@DeleteMapping("/{inventoryId}")
	public ResponseStructure<String> deleteInventory(@PathVariable int inventoryId) {
		inventoryService.deleteInventory(inventoryId);
		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory deleted successfully");
		rs.setData("Inventory deleted successfully with id: " + inventoryId);
		return rs;
	}

	
	@GetMapping("/store/{storeId}")
	public ResponseStructure<List<InventoryResponseDto>> getInventoryByStore(@PathVariable int storeId) {
		List<InventoryResponseDto> list = inventoryService.getInventoryByStore(storeId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched by store");
		rs.setData(list);
		return rs;
	}

	
	@GetMapping("/product/{productId}")
	public ResponseStructure<List<InventoryResponseDto>> getInventoryByProduct(@PathVariable int productId) {
		List<InventoryResponseDto> list = inventoryService.getInventoryByProduct(productId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched by product");
		rs.setData(list);
		return rs;
	}

}
