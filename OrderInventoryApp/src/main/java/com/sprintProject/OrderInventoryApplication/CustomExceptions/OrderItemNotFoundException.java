package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class OrderItemNotFoundException extends RuntimeException {

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}