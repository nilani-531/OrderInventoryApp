package com.sprintProject.orderinventoryapplication.customexception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}


