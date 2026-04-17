package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class ShipmentNotFoundException extends RuntimeException{
	public ShipmentNotFoundException(String message) {
		super(message);
	}
}
