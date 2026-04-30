package com.sprintProject.orderinventoryapplication.customexception;

public class CustomerEmailNotFoundException extends RuntimeException{
	// Custom exception for "Customer Email not found"
	
	public CustomerEmailNotFoundException(String message) {
		super(message);
		// Passes error message to RuntimeException
	}

}


