package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

public interface CustomersServiceInterface {
   
	List<CustomersResponseDto> getAllCustomers();
	// Fetch all customers
	
	CustomersResponseDto getCustomerById(int customerId);
	// Fetch customer using ID
	
	CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto);
	// Create new customer
	
	CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto);
	// Update existing customer
	
	CustomersResponseDto deleteCustomer(int customerId);
	// Delete customer
	
	CustomersResponseDto getCustomerByEmail(String customerEmail); 
	// Fetch customer by email
	
	List<OrdersResponseDto> getCustomerOrders(int customerId);
	// Get all orders of a customer
	
    List<ShipmentsResponseDto> getCustomerShipments(int customerId);
    // Get all shipments of a customer
}