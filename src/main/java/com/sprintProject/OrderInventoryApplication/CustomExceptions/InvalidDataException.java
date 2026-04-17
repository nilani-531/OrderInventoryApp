package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}
