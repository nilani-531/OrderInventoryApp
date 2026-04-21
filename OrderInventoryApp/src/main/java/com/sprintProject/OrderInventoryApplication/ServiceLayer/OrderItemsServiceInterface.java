package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;

public interface OrderItemsServiceInterface {
    // Get all items for a given order
    List<OrderItemsResponseDto> getItemsByOrderId(int orderId);

    // Add item to an order
    OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto);

    // Update in an order
    OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    // Delete item from an order
    void deleteItem(int orderId, int lineItemId);
    
    // Get all items by productId
    List<OrderItemsResponseDto> getItemsByProductId(int productId);

    // Get total quantity of a product across all orders
    Integer getTotalQuantityByProductId(int productId);
}