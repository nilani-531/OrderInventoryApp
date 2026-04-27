package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sprintProject.OrderInventoryApplication.ServiceLayer.CustomersService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/customers")
//base url

public class CustomersController {

	@Autowired
	private CustomersService customersService;

	@PostMapping
	// Handles HTTP post request-> create new customer

	public ResponseEntity<ResponseStructure<CustomersResponseDto>> createCustomer(
			@RequestBody CustomersRequestDto dto) {
		// @RequestBody → converts JSON input to Java object

		CustomersResponseDto response = customersService.createCustomer(dto);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMsg("Customer created successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.CREATED);
	}

	@GetMapping
	// Handles HTTP GET request → fetch all customers

	public ResponseEntity<ResponseStructure<List<CustomersResponseDto>>> getAllCustomers() {

		List<CustomersResponseDto> list = customersService.getAllCustomers();

		ResponseStructure<List<CustomersResponseDto>> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMsg("Customers fetched successfully");
		structure.setData(list);

		return new ResponseEntity<>(structure, HttpStatus.OK);
	}

	@GetMapping("/{customerId}")
	// Fetch customer by ID from URL

	public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerById(@PathVariable int customerId) {

		CustomersResponseDto response = customersService.getCustomerById(customerId);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer fetched successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerByEmail(@RequestParam String email) {
		CustomersResponseDto response = customersService.getCustomerByEmail(email);
		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer fetched successfully");
		responseStructure.setData(response);
		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@PutMapping("/{customerId}")
	// Handles HTTP PUT → update existing customer

	public ResponseEntity<ResponseStructure<CustomersResponseDto>> updateCustomer(@PathVariable int customerId,
			@RequestBody CustomersRequestDto dto) {

		CustomersResponseDto response = customersService.updateCustomer(customerId, dto);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer updated successfully");
		responseStructure.setData(response);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@DeleteMapping("/{customerId}")
	// Handles HTTP DELETE → remove customer

	public ResponseEntity<ResponseStructure<CustomersResponseDto>> deleteCustomer(@PathVariable int customerId) {

		CustomersResponseDto deletedCustomer=customersService.deleteCustomer(customerId);

		ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer deleted successfully");
		responseStructure.setData(deletedCustomer);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@GetMapping("/{customerId}/orders")
	// Fetch all orders of a specific customer

	public ResponseEntity<ResponseStructure<List<OrdersResponseDto>>> getCustomerOrders(@PathVariable int customerId) {

		List<OrdersResponseDto> orders = customersService.getCustomerOrders(customerId);

		ResponseStructure<List<OrdersResponseDto>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer orders fetched successfully");
		responseStructure.setData(orders);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

	@GetMapping("/{customerId}/shipments")
	// Fetch all shipments of a customer

	public ResponseEntity<ResponseStructure<List<ShipmentsResponseDto>>> getCustomerShipments(@PathVariable int customerId) {

		List<ShipmentsResponseDto> shipments = customersService.getCustomerShipments(customerId);

		ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatus(HttpStatus.OK.value());
		responseStructure.setMsg("Customer shipments fetched successfully");
		responseStructure.setData(shipments);

		return new ResponseEntity<>(responseStructure, HttpStatus.OK);
	}

}