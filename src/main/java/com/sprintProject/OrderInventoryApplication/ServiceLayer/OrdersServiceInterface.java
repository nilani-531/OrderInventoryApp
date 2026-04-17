package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;

public interface OrdersServiceInterface {

    OrdersResponseDto createOrder(OrdersRequestDto orderDto);

    List<OrdersResponseDto> getAllOrders();

    OrdersResponseDto getOrderById(int orderId);

    void deleteOrder(int orderId);

    OrdersResponseDto updateOrderStatus(int orderId, OrderStatus status);
}