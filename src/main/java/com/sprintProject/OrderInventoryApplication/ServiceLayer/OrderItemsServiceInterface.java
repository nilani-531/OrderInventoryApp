package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;

public interface OrderItemsServiceInterface {
    // GET all items for a given order
    List<OrderItemsResponseDto> getItemsByOrderId(int orderId);

    // ADD ITEM to an order
    OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto);

    OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    // DELETE ITEM from an order

    void deleteItem(int orderId, int lineItemId);
}