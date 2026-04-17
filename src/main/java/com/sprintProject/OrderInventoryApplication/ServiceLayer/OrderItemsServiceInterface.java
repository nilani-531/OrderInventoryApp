package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;

public interface OrderItemsServiceInterface {

    // ADD ITEM
    OrderItems addItem(int orderId, OrderItemsRequestDto dto);

    // UPDATE ITEM
    OrderItems updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    // DELETE ITEM
    void deleteItem(int orderId, int lineItemId);

 
}