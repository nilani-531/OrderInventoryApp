package com.sprintProject.orderinventoryapplication.service;

import java.util.List;
import com.sprintProject.orderinventoryapplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;

public interface CustomersServiceInterface {
   
   // Fetch all customers
	List<CustomersResponseDto> getAllCustomers();
		
	// Fetch customer using ID
	CustomersResponseDto getCustomerById(int customerId);
	
	// Create new customer
	CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto);
	
	
	// Update existing customer
	CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto);
	
	
	// Delete customer
	CustomersResponseDto deleteCustomer(int customerId);
	
	
	// Fetch customer by email
	CustomersResponseDto getCustomerByEmail(String customerEmail); 
	
	
	// Get all orders of a customer
	List<OrdersResponseDto> getCustomerOrders(int customerId);
	
	
	 // Get all shipments of a customer
    List<ShipmentsResponseDto> getCustomerShipments(int customerId);
   
}

