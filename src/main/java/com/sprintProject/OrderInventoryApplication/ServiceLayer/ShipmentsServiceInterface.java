package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

public interface ShipmentsServiceInterface {
	List<ShipmentsResponseDto> getAllShipments();
	
	ShipmentsResponseDto getShipmentById(int shipmentId);
	
	ShipmentsResponseDto createShipment(ShipmentsRequestDto shipment);

	ShipmentsResponseDto updateShipment(int shipmentId, ShipmentsRequestDto shipment);
	
	String deleteShipment(int shipmentId);
	
	ShipmentsResponseDto updateShipmentStatus(int shipmentId, ShipmentStatus status);
	
	List<ShipmentsResponseDto> getShipmentByCustomerId(int customerId);
	
	List<ShipmentsResponseDto> getShipmentByStoreId(int storeId);

	List<ShipmentsResponseDto> getShipmentByStatus(ShipmentStatus status);
}
