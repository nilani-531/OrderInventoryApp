package com.sprintProject.orderinventoryapplication.customexception;

public class CustomerEmailAlreadyExistException extends RuntimeException{
	// Custom exception for "Customer duplicate email"
	
	public CustomerEmailAlreadyExistException(String message) {
       super(message);
    // Passes error message to RuntimeException
}
}


