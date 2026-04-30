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
	CustomersResponseDto getCustomerById(Integer customerId);
	
	// Create new customer
	CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto);
	
	
	// Update existing customer
	CustomersResponseDto updateCustomer(Integer customerId, CustomersRequestDto customersRequestDto);
	
	
	// Delete customer
	CustomersResponseDto deleteCustomer(Integer customerId);
	
	
	// Fetch customer by email
	CustomersResponseDto getCustomerByEmail(String customerEmail); 
	
	
	// Get all orders of a customer
	List<OrdersResponseDto> getCustomerOrders(Integer customerId);
	
	
	 // Get all shipments of a customer
    List<ShipmentsResponseDto> getCustomerShipments(Integer customerId);
   
}