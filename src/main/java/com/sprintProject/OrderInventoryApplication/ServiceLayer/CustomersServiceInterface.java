package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;

public interface CustomersServiceInterface {
   
	List<CustomersResponseDto> getAllCustomers();
	
	CustomersResponseDto getCustomerById(int customerId);
	
	CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto);
	
	CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto);
	
	void deleteCustomer(int customerId);
	
	CustomersResponseDto getCustomerByEmail(String customerEmail); 
	
	List<Orders> getCustomerOrders(int customerId);
	
    List<Shipments> getCustomerShipments(int customerId);
}
