package com.sprintProject.orderinventoryapplication.customexception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }
}

