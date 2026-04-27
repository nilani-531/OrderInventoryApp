package com.sprintProject.orderinventoryapplication.dto.responseDto;

import java.time.LocalDateTime;

import com.sprintProject.orderinventoryapplication.entity.OrderStatus;

public class OrdersResponseDto {

    private int orderId;

    private int customerId;
   
    private int storeId;
       
    private OrderStatus orderStatusS;

    private LocalDateTime orderTms;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

  
    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public OrderStatus getOrderStatusS() {
        return orderStatusS;
    }

    public void setOrderStatusS(OrderStatus orderStatusS) {
        this.orderStatusS = orderStatusS;
    }

    public LocalDateTime getOrderTms() {
        return orderTms;
    }

    public void setOrderTms(LocalDateTime orderTms) {
        this.orderTms = orderTms;
    }
}

