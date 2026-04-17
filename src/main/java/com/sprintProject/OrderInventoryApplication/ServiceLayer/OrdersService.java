package com.sprintProject.OrderInventoryApplication.ServiceLayer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;

	
	@Service
	public class OrdersService implements OrdersServiceInterface {

	    @Autowired
	    private OrdersRepository repo;

	    @Override
	    public Orders createOrder(Orders order) {
	        order.setOrderStatus(OrderStatus.OPEN);
	        order.setOrderTms(LocalDateTime.now());
	        return repo.save(order);
	    }

	    @Override
	    public List<Orders> getAllOrders() {
	        return repo.findAll();
	    }

	    @Override
	    public Orders getOrderById(int orderId) {
	        return repo.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	    }

	

	    @Override
	    public void deleteOrder(int orderId) {
	        repo.deleteById(orderId);
	    }

	  
	    @Override
	    public Orders updateOrderStatus(int orderId, OrderStatus newStatus) {

	        Orders order = getOrderById(orderId);
	        OrderStatus current = order.getOrderStatus();

	        if ((current == OrderStatus.OPEN && newStatus == OrderStatus.PAID) ||
	            (current == OrderStatus.OPEN && newStatus == OrderStatus.CANCELLED) ||
	            (current == OrderStatus.PAID && newStatus == OrderStatus.SHIPPED) ||
	            (current == OrderStatus.PAID && newStatus == OrderStatus.REFUNDED) ||
	            (current == OrderStatus.SHIPPED && newStatus == OrderStatus.COMPLETE)) {

	            order.setOrderStatus(newStatus);
	        } else {
	            throw new RuntimeException("Invalid status transition");
	        }

	        return repo.save(order);
	    }

		
	}
