package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;

public interface CustomersServiceInterface {
   
	List<CustomersResponseDto> getAllCustomers();
	// Fetch all customers
	
	CustomersResponseDto getCustomerById(int customerId);
	// Fetch customer using ID
	
	CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto);
	// Create new customer
	
	CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto);
	// Update existing customer
	
	String deleteCustomer(int customerId);
	// Delete customer
	
	CustomersResponseDto getCustomerByEmail(String customerEmail); 
	// Fetch customer by email
	
	List<Orders> getCustomerOrders(int customerId);
	// Get all orders of a customer
	
    List<Shipments> getCustomerShipments(int customerId);
    // Get all shipments of a customer
}
