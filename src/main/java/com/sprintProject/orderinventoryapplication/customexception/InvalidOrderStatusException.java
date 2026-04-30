package com.sprintProject.orderinventoryapplication.customexception;


public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}

