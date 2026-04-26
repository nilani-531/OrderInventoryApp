package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.InventoryService;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrdersService;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.ShipmentsService;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.InventoryResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.StoresService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

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

	// GET /api/stores/{storeId}/orders
	// BUG FIX: was calling ordersService.getOrdersByStore() which didn't exist
	// (compilation error) — method name aligned with existing service method.
	@GetMapping("/{storeId}/orders")
	public ResponseStructure<List<OrdersResponseDto>> getOrders(@PathVariable int storeId) {
		List<OrdersResponseDto> data = ordersService.getOrdersByStoreId(storeId);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched successfully");
		rs.setData(data);
		return rs;
	}

	// GET /api/stores/{storeId}/shipments
	// BUG FIX: was completely missing from the controller
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