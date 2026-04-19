package com.sprintProject.OrderInventoryApplication.ControllerClasses;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.CustomersService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;

@RestController

@RequestMapping("/api/customers")
//base url

public class CustomersController {
	
	@Autowired
	private CustomersService customersService;
	
	@PostMapping
    // Handles HTTP post request-> create new customer
   
	public ResponseEntity<ResponseStructure<CustomersResponseDto>> createCustomer(
            @RequestBody CustomersRequestDto dto) {
        //@RequestBody → converts JSON input to Java object
        
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
    
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerById(
            @PathVariable int customerId) {

        CustomersResponseDto response = customersService.getCustomerById(customerId);

        ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMsg("Customer fetched successfully");
        responseStructure.setData(response);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

  
    @GetMapping("/email/{customerEmail}")
    // Fetch customer using email
    
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerByEmail(
            @PathVariable String customerEmail) {

        CustomersResponseDto response = customersService.getCustomerByEmail(customerEmail);

        ResponseStructure<CustomersResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMsg("Customer fetched successfully");
        responseStructure.setData(response);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    // Handles HTTP PUT → update existing customer
    
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> updateCustomer(
            @PathVariable int customerId,
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
    
    public ResponseEntity<ResponseStructure<String>> deleteCustomer(
            @PathVariable int customerId) {

        customersService.deleteCustomer(customerId);

        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMsg("Customer deleted successfully");
        responseStructure.setData("Deleted");

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
	
    @GetMapping("/{customerId}/orders")
    // Fetch all orders of a specific customer
    
    public ResponseEntity<ResponseStructure<List<Orders>>> getCustomerOrders(
            @PathVariable int customerId) {

        List<Orders> orders = customersService.getCustomerOrders(customerId);

        ResponseStructure<List<Orders>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMsg("Customer orders fetched successfully");
        responseStructure.setData(orders);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }
    
    @GetMapping("/{customerId}/shipments")
    // Fetch all shipments of a customer
    
    public ResponseEntity<ResponseStructure<List<Shipments>>> getCustomerShipments(
            @PathVariable int customerId) {

        List<Shipments> shipments = customersService.getCustomerShipments(customerId);

        ResponseStructure<List<Shipments>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(HttpStatus.OK.value());
        responseStructure.setMsg("Customer shipments fetched successfully");
        responseStructure.setData(shipments);

        return new ResponseEntity<>(responseStructure, HttpStatus.OK);
    }


}
