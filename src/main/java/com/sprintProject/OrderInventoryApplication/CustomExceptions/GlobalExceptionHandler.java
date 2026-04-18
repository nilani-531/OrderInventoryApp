package com.sprintProject.OrderInventoryApplication.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  ORDER NOT FOUND
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleOrderNotFound(OrderNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //  ORDER ITEM NOT FOUND
    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleItemNotFound(OrderItemNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //  INVALID ORDER STATUS
    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidStatus(InvalidOrderStatusException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //  INVALID INPUT
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidInput(InvalidInputException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //  RESOURCE ALREADY EXISTS
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResponseStructure<String>> handleAlreadyExists(ResourceAlreadyExistsException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    //  CUSTOMER EMAIL EXISTS
    @ExceptionHandler(CustomerEmailAlreadyExistException.class)
    public ResponseEntity<ResponseStructure<String>> handleEmailAlreadyExists(CustomerEmailAlreadyExistException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //  CUSTOMER EMAIL NOT FOUND
    @ExceptionHandler(CustomerEmailNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleEmailNotFound(CustomerEmailNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //  CUSTOMER ID NOT FOUND
    @ExceptionHandler(CustomerIdNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleIdNotFound(CustomerIdNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // GENERIC EXCEPTION
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleGeneric(Exception ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMsg("Something went wrong");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // SHIPMENT NOT FOUND 
    @ExceptionHandler(ShipmentNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleShipmentNotFound(ShipmentNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    // INVALID STATUS TRANSITION
    @ExceptionHandler(InvalidStatusTransitionException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidStatus(InvalidStatusTransitionException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //DUPLICATE RESOURCE
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseStructure<String>> handleDuplicateResource(DuplicateResourceException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    //INVALID DATA 
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseStructure<String>> handleInvalidData(InvalidDataException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    //STORE NOT FOUND
    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleStoreNotFound(StoreNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    // RESOURCE NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleResourceNotFound(ResourceNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //INVENTORY NOT FOUND
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleInventoryNotFound(InventoryNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    //PRODUCT NOT FOUND 
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleProductNotFound(ProductNotFoundException ex) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMsg(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
}