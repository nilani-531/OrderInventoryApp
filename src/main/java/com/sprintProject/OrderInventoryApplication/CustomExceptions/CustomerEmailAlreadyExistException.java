package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class CustomerEmailAlreadyExistException extends RuntimeException{
   public CustomerEmailAlreadyExistException(String message) {
       super(message);
}
}
