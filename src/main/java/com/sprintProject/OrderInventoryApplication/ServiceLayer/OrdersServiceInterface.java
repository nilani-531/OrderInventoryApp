package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.time.LocalDateTime;
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

    OrdersResponseDto updateOrderStore(int orderId, int storeId);

    OrdersResponseDto updateOrderCustomer(int orderId, int customerId);

    List<OrdersResponseDto> getOrdersByCustomerId(int customerId);

    List<OrdersResponseDto> getOrdersByStoreId(int storeId);

    List<OrdersResponseDto> getOrdersByStatus(OrderStatus status);

    List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end);
}