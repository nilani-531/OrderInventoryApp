package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public interface ShipmentsServiceInterface {
	List<Shipments> getAllShipments();
	
	Shipments getShipmentById(int shipmentId);
	
	Shipments createShipment(Shipments shipment);

	Shipments updateShipment(int shipmentId, Shipments shipment);
	
	void deleteShipment(int shipmentId);
	
	List<Shipments> getShipmentByCustomerId(int customerId);
	
	List<Shipments> getShipmentByStoreId(int storeId);

	List<Shipments> getShipmentByStatus(ShipmentStatus status);
}
