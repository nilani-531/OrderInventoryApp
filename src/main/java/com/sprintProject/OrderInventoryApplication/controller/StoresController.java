package com.sprintProject.orderinventoryapplication.controller;

import java.util.List;
import com.sprintProject.orderinventoryapplication.service.InventoryService;
import com.sprintProject.orderinventoryapplication.service.OrdersService;
import com.sprintProject.orderinventoryapplication.service.ShipmentsService;
import com.sprintProject.orderinventoryapplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.orderinventoryapplication.service.StoresService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.StoresResponseDto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/stores")
public class StoresController {

	@Autowired
	private StoresService service;

	@Autowired
	private InventoryService inventoryService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private ShipmentsService shipmentsService;

	// Create Store
	@PostMapping
	public ResponseStructure<StoresResponseDto> createStore(@RequestBody StoresRequestDto dto) {
		StoresResponseDto response = service.createStore(dto);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(201);
		rs.setMsg("Store created successfully");
		rs.setData(response);

		return rs;
	}

	// Get Store by ID
	@GetMapping("/{id}")
	public ResponseStructure<StoresResponseDto> getStore(@PathVariable int id) {
		StoresResponseDto response = service.getStoreById(id);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store fetched successfully");
		rs.setData(response);

		return rs;
	}

	// Get All Stores
	@GetMapping
	public ResponseStructure<List<StoresResponseDto>> getAllStores() {
		List<StoresResponseDto> list = service.getAllStores();

		ResponseStructure<List<StoresResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Stores fetched successfully");
		rs.setData(list);

		return rs;
	}

	// Update Store
	@PutMapping("/{id}")
	public ResponseStructure<StoresResponseDto> updateStore(@PathVariable int id, @RequestBody StoresRequestDto dto) {

		StoresResponseDto response = service.updateStore(id, dto);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store updated successfully");
		rs.setData(response);

		return rs;
	}

	// Delete Store
	@DeleteMapping("/{id}")
	public ResponseStructure<String> deleteStore(@PathVariable int id) {
		service.deleteStore(id);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store deleted successfully");
		rs.setData("Store deleted successfully with id: " + id);

		return rs;
	}

	// GET /api/stores/{storeId}/inventory
	@GetMapping("/{storeId}/inventory")
	public ResponseStructure<List<InventoryResponseDto>> getInventory(@PathVariable int storeId) {
		List<InventoryResponseDto> list = inventoryService.getInventoryByStore(storeId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(list);
		return rs;
	}
	
	// Get Store by Name
	@GetMapping("/name/{storeName}")
	public ResponseStructure<StoresResponseDto> getStoreByName(@PathVariable String storeName) {

		StoresResponseDto response = service.getStoreByName(storeName);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store fetched successfully by name");
		rs.setData(response);

		return rs;
	}

	// Get orders by store ID
	@GetMapping("/{storeId}/orders")
	public ResponseStructure<List<OrdersResponseDto>> getOrders(@PathVariable int storeId) {
		List<OrdersResponseDto> data = ordersService.getOrdersByStoreId(storeId);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched successfully");
		rs.setData(data);
		return rs;
	}

	// Get shipments by store ID
	@GetMapping("/{storeId}/shipments")
	public ResponseStructure<List<ShipmentsResponseDto>> getShipments(@PathVariable int storeId) {
		List<ShipmentsResponseDto> data = shipmentsService.getShipmentByStoreId(storeId);
		ResponseStructure<List<ShipmentsResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Shipments fetched successfully");
		rs.setData(data);
		return rs;
	}


}

