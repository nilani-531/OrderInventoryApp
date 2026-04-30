package com.sprintProject.orderinventoryapplication.service;

import java.util.List;

import com.sprintProject.orderinventoryapplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrderItemsResponseDto;

public interface OrderItemsServiceInterface {
    // Get all items for a given order
    List<OrderItemsResponseDto> getItemsByOrderId(Integer orderId);

    // Add item to an order
    OrderItemsResponseDto addItem(Integer orderId, Integer productId, OrderItemsRequestDto dto);

    // Update in an order
    OrderItemsResponseDto updateItem(Integer orderId, Integer lineItemId, OrderItemsRequestDto dto);

    // Delete item from an order
    void deleteItem(Integer orderId, Integer lineItemId);
    
    // Get all items by productId
    List<OrderItemsResponseDto> getItemsByProductId(Integer productId);

    // Get total quantity of a product across all orders
    Integer getTotalQuantityByProductId(Integer productId);
}

