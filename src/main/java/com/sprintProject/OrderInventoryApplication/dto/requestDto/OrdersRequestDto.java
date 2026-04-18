package com.sprintProject.OrderInventoryApplication.dto.requestDto;

import java.time.LocalDateTime;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;

public class OrdersRequestDto {

    private int customerId;
    private int storeId;
    private OrderStatus orderStatusS;
    private LocalDateTime orderTms;

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