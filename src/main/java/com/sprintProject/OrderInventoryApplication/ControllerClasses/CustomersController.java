package com.sprintProject.OrderInventoryApplication.ControllerClasses;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	
	@Autowired
	private CustomersService customersService;
	
	@PostMapping()
	public Customers createCustomer(@RequestBody Customers customers) {
		return customersService.createCustomer(customers);
	}
	@GetMapping()
	public List<Customers> getAllCustomers(){
		return customersService.getAllCustomers();
	}
	@GetMapping("/{customerId}")
	public Customers getCustomerById(@PathVariable int customerId) {
		return customersService.getCustomerById(customerId);
	}
	@GetMapping("/{customerEmail}")
	public Customers getCustomerByEmail(@PathVariable String customeremail) {
		return customersService.getCustomerByEmail(customeremail);
	}
	@GetMapping("/{customerId}")
	public List<Orders> getCustomerOrders(@PathVariable int CustomerId){
		return customersService.getCustomerOrders(CustomerId);
	}
	@GetMapping("/{customerId}")
	public List<Shipments> getCustomerShipments(@PathVariable int CustomerId){
		return customersService.getCustomerShipments(CustomerId);
	}
	@PutMapping("/{customerId}")
	public Customers updateCustomers(@PathVariable int customerId,@RequestBody Customers customers) {
		return customersService.updateCustomer(customerId,customers);
	}
	@DeleteMapping("/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		customersService.deleteCustomer(customerId);
		return "Account removed Successfully";
	}
	
	

}
