package com.sprintProject.orderinventoryapplication.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sprintProject.orderinventoryapplication.entity.OrderStatus;
import com.sprintProject.orderinventoryapplication.service.OrdersServiceInterface;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

	@Autowired
	private OrdersServiceInterface ordersServiceInterface;

	// Create Order
	@PostMapping
	public ResponseStructure<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto dto) {

		OrdersResponseDto response = ordersServiceInterface.createOrder(dto);

		ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(201);
		rs.setMsg("Order created successfully");
		rs.setData(response);

		return rs;
	}

	@GetMapping("/count/{status}")
	public ResponseEntity<Long> getCount(@PathVariable OrderStatus status) {
		long count = ordersServiceInterface.getOrdersCountByStatus(status);
		return ResponseEntity.ok(count);
	}

	// Get all Orders
	@GetMapping
	public ResponseStructure<List<OrdersResponseDto>> getAllOrders() {

		List<OrdersResponseDto> list = ordersServiceInterface.getAllOrders();

		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched successfully");
		rs.setData(list);

		return rs;
	}

	// Get Order by id
	@GetMapping("/{orderId}")
	public ResponseStructure<OrdersResponseDto> getOrder(@PathVariable int orderId) {

		OrdersResponseDto response = ordersServiceInterface.getOrderById(orderId);

		ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Order fetched successfully");
		rs.setData(response);

		return rs;
	}

	// Delete Order
	@DeleteMapping("/{orderId}")
	public ResponseStructure<String> deleteOrder(@PathVariable int orderId) {

		ordersServiceInterface.deleteOrder(orderId);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Order deleted successfully");
		rs.setData("Deleted");

		return rs;
	}

	// Update Order - storeId, customerId, or orderTms
	@PutMapping("/{orderId}")
	public ResponseStructure<OrdersResponseDto> updateOrder(@PathVariable int orderId,
			@RequestParam(required = false) Integer storeId,
			@RequestParam(required = false) Integer customerId,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderTms) {

		OrdersResponseDto response;
		if (storeId != null) {
			response = ordersServiceInterface.updateOrderStore(orderId, storeId);
		} else if (customerId != null) {
			response = ordersServiceInterface.updateOrderCustomer(orderId, customerId);
		} else if (orderTms != null) {
			response = ordersServiceInterface.updateOrderTms(orderId, orderTms);
		} else {
			throw new RuntimeException("Provide at least storeId, customerId, or orderTms to update");
		}

		ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Order updated successfully");
		rs.setData(response);
		return rs;
	}

	// Get orders count by status
	@GetMapping("/count")
	public ResponseStructure<Long> getOrdersCountByStatus(@RequestParam OrderStatus status) {

		long count = ordersServiceInterface.getOrdersCountByStatus(status);

		ResponseStructure<Long> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(200);
		responseStructure.setMsg("Orders count fetched successfully");
		responseStructure.setData(count);

		return responseStructure;
	}

	// Fetch orders by customer ID
	@GetMapping("/customer/{customerId}")
	public ResponseStructure<List<OrdersResponseDto>> getOrdersByCustomer(@PathVariable int customerId) {
		List<OrdersResponseDto> list = ordersServiceInterface.getOrdersByCustomerId(customerId);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched by customer");
		rs.setData(list);
		return rs;
	}

	// Fetch orders by store ID
	@GetMapping("/store/{storeId}")
	public ResponseStructure<List<OrdersResponseDto>> getOrdersByStore(@PathVariable int storeId) {
		List<OrdersResponseDto> list = ordersServiceInterface.getOrdersByStoreId(storeId);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched by store");
		rs.setData(list);
		return rs;
	}

	// Fetch orders by status
	@GetMapping("/status/{status}")
	public ResponseStructure<List<OrdersResponseDto>> getOrdersByStatus(@PathVariable OrderStatus status) {
		List<OrdersResponseDto> list = ordersServiceInterface.getOrdersByStatus(status);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched by status");
		rs.setData(list);
		return rs;
	}

	// Fetch orders by date range
	@GetMapping("/date-range")
	public ResponseStructure<List<OrdersResponseDto>> getOrdersByDateRange(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

		List<OrdersResponseDto> list = ordersServiceInterface.getOrdersBetweenDates(from, to);
		ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Orders fetched by date range");
		rs.setData(list);
		return rs;
	}

	// Update order status
	@PatchMapping("/{orderId}/status")
	public ResponseStructure<OrdersResponseDto> updateStatus(@PathVariable int orderId,
			@RequestParam OrderStatus status) {

		OrdersResponseDto response = ordersServiceInterface.updateOrderStatus(orderId, status);
		ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
		rs.setStatus(200);
		rs.setMsg("Order status updated successfully");
		rs.setData(response);
		return rs;
	}
}

