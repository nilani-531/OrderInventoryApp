package com.sprintProject.orderinventoryapplication.customexception;

public class InventoryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InventoryNotFoundException(String message) {
		super(message);
	}

	public InventoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}


