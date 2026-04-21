package com.sprintProject.OrderInventoryApplication.EntityClasses;

public enum ShipmentStatus {
	CREATED,
	SHIPPED,
	IN_TRANSIT,
	DELIVERED;
	
	 public boolean isValidTransition(ShipmentStatus next) {
        return switch (this) {
            case CREATED -> next == SHIPPED;
            case SHIPPED -> next == IN_TRANSIT;
            case IN_TRANSIT -> next == DELIVERED;
            case DELIVERED -> false; 
        };
    }
}
