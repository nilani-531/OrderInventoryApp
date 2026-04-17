package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class CustomerEmailNotFoundException extends RuntimeException{
	public CustomerEmailNotFoundException(String message) {
		super(message);
	}

}
