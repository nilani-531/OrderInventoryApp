package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;

public interface OrderItemsServiceInterface {

    List<OrderItemsResponseDto> getItemsByOrderId(int orderId);

    OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto);

    OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto);

    void deleteItem(int orderId, int lineItemId);
}