package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;

public interface OrderItemsServiceInterface {
	

	    OrderItems addItem(int orderId, OrderItems item);

	    OrderItems updateItem(int orderId, int lineItemId, OrderItems item);

	    void deleteItem(int orderId, int lineItemId);
}
