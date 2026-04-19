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
public class CustomersController {
	
	@Autowired
	private CustomersService customersService;
	
	
	@PostMapping
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> createCustomer(
            @RequestBody CustomersRequestDto dto) {

        CustomersResponseDto response = customersService.createCustomer(dto);

        ResponseStructure<CustomersResponseDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.CREATED.value());
        structure.setMsg("Customer created successfully");
        structure.setData(response);

        return new ResponseEntity<>(structure, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<CustomersResponseDto>>> getAllCustomers() {

        List<CustomersResponseDto> list = customersService.getAllCustomers();

        ResponseStructure<List<CustomersResponseDto>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customers fetched successfully");
        structure.setData(list);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

 
    @GetMapping("/{customerId}")
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerById(
            @PathVariable int customerId) {

        CustomersResponseDto response = customersService.getCustomerById(customerId);

        ResponseStructure<CustomersResponseDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer fetched successfully");
        structure.setData(response);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

  
    @GetMapping("/email/{customerEmail}")
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> getCustomerByEmail(
            @PathVariable String customerEmail) {

        CustomersResponseDto response = customersService.getCustomerByEmail(customerEmail);

        ResponseStructure<CustomersResponseDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer fetched successfully");
        structure.setData(response);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<ResponseStructure<CustomersResponseDto>> updateCustomer(
            @PathVariable int customerId,
            @RequestBody CustomersRequestDto dto) {

        CustomersResponseDto response = customersService.updateCustomer(customerId, dto);

        ResponseStructure<CustomersResponseDto> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer updated successfully");
        structure.setData(response);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<ResponseStructure<String>> deleteCustomer(
            @PathVariable int customerId) {

        customersService.deleteCustomer(customerId);

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer deleted successfully");
        structure.setData("Deleted");

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
	
    @GetMapping("/{customerId}/orders")
    public ResponseEntity<ResponseStructure<List<Orders>>> getCustomerOrders(
            @PathVariable int customerId) {

        List<Orders> orders = customersService.getCustomerOrders(customerId);

        ResponseStructure<List<Orders>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer orders fetched successfully");
        structure.setData(orders);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }
    
    @GetMapping("/{customerId}/shipments")
    public ResponseEntity<ResponseStructure<List<Shipments>>> getCustomerShipments(
            @PathVariable int customerId) {

        List<Shipments> shipments = customersService.getCustomerShipments(customerId);

        ResponseStructure<List<Shipments>> structure = new ResponseStructure<>();
        structure.setStatus(HttpStatus.OK.value());
        structure.setMsg("Customer shipments fetched successfully");
        structure.setData(shipments);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }


}
