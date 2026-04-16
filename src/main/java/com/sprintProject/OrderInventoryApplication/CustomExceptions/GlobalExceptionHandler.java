package com.sprintProject.OrderInventoryApplication.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderNotFound(OrderNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleItemNotFound(OrderItemNotFoundException ex) {
        return ex.getMessage();
    }



    @ExceptionHandler(InvalidOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidStatus(InvalidOrderStatusException ex) {
        return ex.getMessage();
    }



    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidInput(InvalidInputException ex) {
        return ex.getMessage();
    }



    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExists(ResourceAlreadyExistsException ex) {
        return ex.getMessage();
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneric(Exception ex) {
        return "Something went wrong";
    }
}