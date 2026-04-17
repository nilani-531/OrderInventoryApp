package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrdersService;

	


	@RestController
	@RequestMapping("/api/orders")
	public class OrdersController {

	    @Autowired
	    private OrdersService service;

	    // GET ALL
	    @GetMapping
	    public List<Orders> getAllOrders() {
	        return service.getAllOrders();
	    }

	    // GET BY ID
	    @GetMapping("/{orderId}")
	    public Orders getOrder(@PathVariable int orderId) {
	        return service.getOrderById(orderId);
	    }

	    // CREATE
	    @PostMapping
	    public Orders createOrder(@RequestBody Orders order) {
	        return service.createOrder(order);
	    }

	  

	    //  DELETE
	    @DeleteMapping("/{orderId}")
	    public String deleteOrder(@PathVariable int orderId) {
	        service.deleteOrder(orderId);
	        return "Order deleted successfully";
	    }

	 }
