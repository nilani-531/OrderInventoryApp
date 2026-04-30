package com.sprintProject.orderinventoryapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sprintProject.orderinventoryapplication.service.CustomersServiceInterface;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

import com.sprintProject.orderinventoryapplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;

@RestController
@Validated
@RequestMapping("/api/customers")


public class CustomersController {

	@Autowired
	private CustomersServiceInterface customersServiceInterface;

	
	// Handles HTTP post request-> create new customer
    @PostMapping
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> createCustomer(
		@Valid	@RequestBody CustomersRequestDto dto) {
		// @RequestBody → converts JSON input to Java object

		CustomersResponseDto response = customersServiceInterface.createCustomer(dto);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMsg("Customer created successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	
	// Handles HTTP GET request → fetch all customers
    @GetMapping 
	public ResponseEntity<ResponseStructure<List<CustomersResponseDto>>> getAllCustomers() {

		List<CustomersResponseDto> list = customersServiceInterface.getAllCustomers();

		ResponseStructure<List<CustomersResponseDto>> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMsg("Customers fetched successfully");
		structure.setData(list);

		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	// Fetch customer by ID from URL
    	@GetMapping("/{customerId}")
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerById(@PathVariable   Integer customerId) {

		CustomersResponseDto response = customersServiceInterface.getCustomerById(customerId);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer fetched successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	
	// Fetch customer by email address
   @GetMapping("/search")
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerByEmail(@RequestParam  String email) {
		CustomersResponseDto response = customersServiceInterface.getCustomerByEmail(email);
		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer fetched successfully");
		responseStructure.setData(response);
		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	
	// Handles HTTP PUT → update existing customer
    @PutMapping("/{customerId}")
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> updateCustomer(@PathVariable  Integer customerId,
		@Valid	@RequestBody CustomersRequestDto dto) {

		CustomersResponseDto response = customersServiceInterface.updateCustomer(customerId, dto);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer updated successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

    // Handles HTTP DELETE → remove customer
	@DeleteMapping("/{customerId}")
		public ResponseEntity<ResponseStructure<CustomersResponseDto>> deleteCustomer(@PathVariable  Integer customerId) {

		CustomersResponseDto deletedCustomer=customersServiceInterface.deleteCustomer(customerId);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer deleted successfully");
		responseStructure.setData(deletedCustomer);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	
	// Fetch all orders of a specific customer
    @GetMapping("/{customerId}/orders")
	public ResponseEntity<ResponseStructure<List<OrdersResponseDto>>> getCustomerOrders(@PathVariable  Integer customerId) {

		List<OrdersResponseDto> orders = customersServiceInterface.getCustomerOrders(customerId);

		ResponseStructure<List<OrdersResponseDto>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer orders fetched successfully");
		responseStructure.setData(orders);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

    // Fetch all shipments of a customer
	@GetMapping("/{customerId}/shipments")
	public ResponseEntity<ResponseStructure<List<ShipmentsResponseDto>>> getCustomerShipments(@PathVariable  Integer customerId) {

		List<ShipmentsResponseDto> shipments = customersServiceInterface.getCustomerShipments(customerId);

		ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer shipments fetched successfully");
		responseStructure.setData(shipments);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

}