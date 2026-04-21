package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class CustomerIdNotFoundException extends RuntimeException{
	// Custom exception class for "Customer ID not found"
	
	public CustomerIdNotFoundException(String message) {
		super(message);
		// Calls parent (RuntimeException) constructor and passes message
	}

}
