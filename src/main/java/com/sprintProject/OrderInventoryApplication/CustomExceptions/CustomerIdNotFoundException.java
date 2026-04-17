package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class CustomerIdNotFoundException extends RuntimeException{

	public CustomerIdNotFoundException(String message) {
		super(message);
	}

}
