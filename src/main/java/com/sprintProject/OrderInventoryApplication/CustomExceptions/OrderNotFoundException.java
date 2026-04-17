package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}