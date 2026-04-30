package com.sprintProject.orderinventoryapplication.service;

import java.time.LocalDateTime;
import java.util.List;

import com.sprintProject.orderinventoryapplication.entity.OrderStatus;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;

public interface OrdersServiceInterface {
	//To Create the Order
    OrdersResponseDto createOrder(OrdersRequestDto orderDto);
   
    //To View All Orders
    List<OrdersResponseDto> getAllOrders();
    
    //Get By Order id
    OrdersResponseDto getOrderById(Integer orderId);
    
    //Delete the Order
    void deleteOrder(Integer orderId);
    
    //Update Order status 
    OrdersResponseDto updateOrderStatus(Integer orderId, OrderStatus status);
    
    //Update Order by Order id,Store id
    OrdersResponseDto updateOrderStore(Integer orderId, Integer storeId);
    
    // Update Order by Order id,Customer id
    OrdersResponseDto updateOrderCustomer(Integer orderId, Integer customerId);

    // Update Order timestamp
    OrdersResponseDto updateOrderTms(Integer orderId, LocalDateTime orderTms);

    //Get Order by Customer
    List<OrdersResponseDto> getOrdersByCustomerId(Integer customerId);
    
    //Get Order by Store id
    List<OrdersResponseDto> getOrdersByStoreId(Integer storeId);
    
    //Get Orders by Order status
    List<OrdersResponseDto> getOrdersByStatus(OrderStatus status);

    //Get Orders between dates
    List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end);
    
    // Get orders count by status
    long getOrdersCountByStatus(OrderStatus status);
}

