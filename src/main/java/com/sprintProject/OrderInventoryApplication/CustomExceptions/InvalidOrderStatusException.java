package com.sprintProject.OrderInventoryApplication.CustomExceptions;


public class InvalidOrderStatusException extends RuntimeException {

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}