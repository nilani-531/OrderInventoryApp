package com.sprintProject.orderinventoryapplication.customexception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}

