package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrderItemsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;

	@Service
	public class OrderItemsService implements OrderItemsServiceInterface {

	    @Autowired
	    private OrderItemsRepository itemRepo;

	    @Autowired
	    private OrdersRepository orderRepo;

	   

	    // ADD ITEM
	    @Override
	    public OrderItems addItem(int orderId, OrderItems item) {

	        Orders order = orderRepo.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));

	        item.setOrders(order);
	        return itemRepo.save(item);
	    }

	    // UPDATE ITEM
	    @Override
	    public OrderItems updateItem(int orderId, int lineItemId, OrderItems updated) {

	        OrderItems item = itemRepo.findById(lineItemId)
	                .orElseThrow(() -> new RuntimeException("Item not found"));

	        item.setQuantity(updated.getQuantity());
	        item.setUnitPrice(updated.getUnitPrice());

	        return itemRepo.save(item);
	    }

	    // DELETE ITEM
	    @Override
	    public void deleteItem(int orderId, int lineItemId) {
	        itemRepo.deleteById(lineItemId);
	    }

		
	}
