package com.sprintProject.OrderInventoryApplication.CustomExceptions;

public class StoreNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StoreNotFoundException(String message) {
		super(message);
	}

	public StoreNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
