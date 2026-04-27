package com.sprintProject.orderinventoryapplication.customexception;

public class InvalidStatusTransitionException extends RuntimeException{
	
	public InvalidStatusTransitionException(String message) {
		super(message);
	}
}


