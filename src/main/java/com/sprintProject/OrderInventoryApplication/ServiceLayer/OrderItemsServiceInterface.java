package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

<<<<<<< HEAD
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;

public interface OrderItemsServiceInterface {

    // ADD ITEM
    OrderItems addItem(int orderId, OrderItemsRequestDto dto);

    // UPDATE ITEM
    OrderItems updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    // DELETE ITEM
    void deleteItem(int orderId, int lineItemId);

 
=======
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;

public interface OrderItemsServiceInterface {

    // GET all items for a given order
    List<OrderItemsResponseDto> getItemsByOrderId(int orderId);

    // ADD ITEM to an order
    OrderItemsResponseDto addItem(int orderId, OrderItemsRequestDto dto);

    // UPDATE ITEM in an order
    OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    // DELETE ITEM from an order
    void deleteItem(int orderId, int lineItemId);
}