package com.sprintProject.orderinventoryapplication.controller;

import java.util.List;
import com.sprintProject.orderinventoryapplication.service.InventoryServiceInterface;
import com.sprintProject.orderinventoryapplication.service.OrdersServiceInterface;
import com.sprintProject.orderinventoryapplication.service.ShipmentsServiceInterface;
import com.sprintProject.orderinventoryapplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.orderinventoryapplication.service.StoresServiceInterface;

import jakarta.validation.Valid;

import com.sprintProject.orderinventoryapplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.StoresResponseDto;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/stores")
public class StoresController {

	@Autowired
	private StoresServiceInterface storesServiceInterface;

	@Autowired
	private InventoryServiceInterface inventoryServiceInterface;

	@Autowired
	private OrdersServiceInterface ordersServiceInterface;

	@Autowired
	private ShipmentsServiceInterface shipmentsServiceInterface;

	// Create Store
	@PostMapping
	public ResponseStructure<StoresResponseDto> createStore(@Valid @RequestBody StoresRequestDto dto) {
		StoresResponseDto response = storesServiceInterface.createStore(dto);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(201);
		rs.setMsg("Store created successfully");
		rs.setData(response);

		return rs;
	}

	// Get Store by ID
	@GetMapping("/{id}")
	public ResponseStructure<StoresResponseDto> getStore(@PathVariable int id) {
		StoresResponseDto response = storesServiceInterface.getStoreById(id);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store fetched successfully");
		rs.setData(response);

		return rs;
	}

	// Get All Stores
	@GetMapping
	public ResponseStructure<List<StoresResponseDto>> getAllStores() {
		List<StoresResponseDto> list = storesServiceInterface.getAllStores();

		ResponseStructure<List<StoresResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Stores fetched successfully");
		rs.setData(list);

		return rs;
	}

	// Update Store
	@PutMapping("/{id}")
	public ResponseStructure<StoresResponseDto> updateStore(@PathVariable Integer id, @Valid @RequestBody StoresRequestDto dto) {

		StoresResponseDto response = storesServiceInterface.updateStore(id, dto);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store updated successfully");
		rs.setData(response);

		return rs;
	}

	// Delete Store
	@DeleteMapping("/{id}")
	public ResponseStructure<String> deleteStore(@PathVariable Integer id) {
		storesServiceInterface.deleteStore(id);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store deleted successfully");
		rs.setData("Store deleted successfully with id: " + id);

		return rs;
	}

	// GET /api/stores/{storeId}/inventory
	@GetMapping("/{storeId}/inventory")
	public ResponseStructure<List<InventoryResponseDto>> getInventory(@PathVariable Integer storeId) {
		List<InventoryResponseDto> list = inventoryServiceInterface.getInventoryByStore(storeId);
		ResponseStructure<List<InventoryResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Inventory fetched successfully");
		rs.setData(list);
		return rs;
	}
	
	// Get Store by Name
	@GetMapping("/name/{storeName}")
	public ResponseStructure<StoresResponseDto> getStoreByName(@PathVariable String storeName) {

		StoresResponseDto response = storesServiceInterface.getStoreByName(storeName);

		ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Store fetched successfully by name");
		rs.setData(response);

		return rs;
	}

	// Get orders by store ID
	@GetMapping("/{storeId}/orders")
	public ResponseStructure<List<OrdersResponseDto>> getOrders(@PathVariable Integer storeId) {
		List<OrdersResponseDto> data = ordersServiceInterface.getOrdersByStoreId(storeId);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched successfully");
		rs.setData(data);
		return rs;
	}

	// Get shipments by store ID
	@GetMapping("/{storeId}/shipments")
	public ResponseStructure<List<ShipmentsResponseDto>> getShipments(@PathVariable Integer storeId) {
		List<ShipmentsResponseDto> data = shipmentsServiceInterface.getShipmentByStoreId(storeId);
		ResponseStructure<List<ShipmentsResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Shipments fetched successfully");
		rs.setData(data);
		return rs;
	}


}

