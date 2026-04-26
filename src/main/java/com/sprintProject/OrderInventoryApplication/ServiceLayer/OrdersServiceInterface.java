package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.time.LocalDateTime;
import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;

public interface OrdersServiceInterface {
	//To Create the Order
    OrdersResponseDto createOrder(OrdersRequestDto orderDto);
   
    //To View All Orders
    List<OrdersResponseDto> getAllOrders();
    
    //Get By Order id
    OrdersResponseDto getOrderById(int orderId);
    
    //Delete the Order
    void deleteOrder(int orderId);
    
    //Update Order status 
    OrdersResponseDto updateOrderStatus(int orderId, OrderStatus status);
    
    //Update Order by Order id,Store id
    OrdersResponseDto updateOrderStore(int orderId, int storeId);
    
    // Update Order by Order id,Customer id
    OrdersResponseDto updateOrderCustomer(int orderId, int customerId);

    // Update Order timestamp
    OrdersResponseDto updateOrderTms(int orderId, LocalDateTime orderTms);

    //Get Order by Customer
    List<OrdersResponseDto> getOrdersByCustomerId(int customerId);
    
    //Get Order by Store id
    List<OrdersResponseDto> getOrdersByStoreId(int storeId);
    
    //Get Orders by Order status
    List<OrdersResponseDto> getOrdersByStatus(OrderStatus status);

    //Get Orders between dates
    List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end);
    
    // Get orders count by status
    long getOrdersCountByStatus(OrderStatus status);
}