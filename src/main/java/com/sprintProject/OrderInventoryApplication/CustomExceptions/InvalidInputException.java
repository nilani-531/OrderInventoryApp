package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}