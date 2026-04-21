package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

public interface ShipmentsServiceInterface {
	
	// Retrieve all shipments
    List<ShipmentsResponseDto> getAllShipments();

    // Retrieve a shipment by its ID
    ShipmentsResponseDto getShipmentById(int shipmentId);

    // Create a new shipment
    ShipmentsResponseDto createShipment(ShipmentsRequestDto shipment);

    // Update shipment details
    ShipmentsResponseDto updateShipment(int shipmentId, ShipmentsRequestDto shipment);

    // Delete shipment by ID
    String deleteShipment(int shipmentId);

    // Update shipment status with validation
    ShipmentsResponseDto updateShipmentStatus(int shipmentId, ShipmentStatus status);

    // Get shipments for a specific customer
    List<ShipmentsResponseDto> getShipmentByCustomerId(int customerId);

    // Get shipments for a specific store
    List<ShipmentsResponseDto> getShipmentByStoreId(int storeId);

    // Get shipments based on status
    List<ShipmentsResponseDto> getShipmentByStatus(ShipmentStatus status);
}
