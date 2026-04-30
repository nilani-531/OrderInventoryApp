package com.sprintProject.orderinventoryapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.orderinventoryapplication.customexception.InvalidStatusTransitionException;
import com.sprintProject.orderinventoryapplication.customexception.ShipmentNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Customers;
import com.sprintProject.orderinventoryapplication.entity.ShipmentStatus;
import com.sprintProject.orderinventoryapplication.entity.Shipments;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository;
import com.sprintProject.orderinventoryapplication.repository.ShipmentsRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
import com.sprintProject.orderinventoryapplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;


@Service
public class ShipmentsService implements ShipmentsServiceInterface{
	@Autowired
	private ShipmentsRepository shipmentsRepository;
	
	 @Autowired
	 private CustomersRepository customersRepository;
	 
	 @Autowired
	 private StoresRepository storesRepository;

	// Get all shipments and convert Entity -> DTO
	public List<ShipmentsResponseDto> getAllShipments(){
		return shipmentsRepository.findAll().stream().map(this::mapToResponse).toList();
	}
    
	// Fetch shipment or throw exception if not found
    public ShipmentsResponseDto getShipmentById(int shipmentId) {
    	
        Shipments shipment= shipmentsRepository.findById(shipmentId)
        		.orElseThrow(()-> new ShipmentNotFoundException("Shipment not found: "+ shipmentId));
        
        return mapToResponse(shipment);
    }
    
    // Create shipment with default status CREATED
    public ShipmentsResponseDto createShipment(ShipmentsRequestDto shipmentsRequestDto) {
    	
    	 // Validate customer existence
    	Customers customer = customersRepository.findById(shipmentsRequestDto.getCustomerId())
                .orElseThrow(() -> new ShipmentNotFoundException("Customer not found: " + shipmentsRequestDto.getCustomerId()));

    	 // Validate store existence
        Stores store = storesRepository.findById(shipmentsRequestDto.getStoreId())
                .orElseThrow(() -> new ShipmentNotFoundException("Store not found: " + shipmentsRequestDto.getStoreId()));

        Shipments shipment = new Shipments();
        shipment.setCustomers(customer);
        shipment.setStores(store);
        shipment.setDeliveryAddress(shipmentsRequestDto.getDeliveryAddress());
        // Initial status
        shipment.setShipmentStatus(ShipmentStatus.CREATED);
        Shipments savedShipment = shipmentsRepository.save(shipment);
        
        return mapToResponse(savedShipment);
    }
   
    // Update shipment
    public ShipmentsResponseDto updateShipment(int shipmentId, ShipmentsRequestDto shipmentsRequestDto) {
    	
    	Shipments existingShipment = shipmentsRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found: " + shipmentId));

        if (shipmentsRequestDto.getDeliveryAddress() != null) {
        	existingShipment.setDeliveryAddress(shipmentsRequestDto.getDeliveryAddress());
        }

        if (shipmentsRequestDto.getCustomerId() != 0) {
            Customers customer = customersRepository.findById(shipmentsRequestDto.getCustomerId())
                    .orElseThrow(() -> new ShipmentNotFoundException("Customer not found: " + shipmentsRequestDto.getCustomerId()));
            existingShipment.setCustomers(customer);
        }

        if (shipmentsRequestDto.getStoreId() != 0) {
            Stores store = storesRepository.findById(shipmentsRequestDto.getStoreId())
                    .orElseThrow(() -> new ShipmentNotFoundException("Store not found: " + shipmentsRequestDto.getStoreId()));

            existingShipment.setStores(store);
        }

        return mapToResponse(shipmentsRepository.save(existingShipment));
    }
    
    // Delete after checking existence
    public String deleteShipment(int shipmentId) {

        if (!shipmentsRepository.existsById(shipmentId)) {
            throw new ShipmentNotFoundException("Shipment not found: " + shipmentId);
        }

        shipmentsRepository.deleteById(shipmentId);

        return "Shipment deleted successfully with id: " + shipmentId;
    }
    
    // status transition validation
    public ShipmentsResponseDto updateShipmentStatus(int shipmentId, ShipmentStatus shipmentStatus) {

        Shipments shipment = shipmentsRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException("Shipment not found: " + shipmentId));

        // Checking valid status flow
        if (!shipment.getShipmentStatus().isValidTransition(shipmentStatus)) {
            throw new InvalidStatusTransitionException("Cannot change status from " + shipment.getShipmentStatus() + " to " + shipmentStatus);
        }

        shipment.setShipmentStatus(shipmentStatus);

        Shipments updatedShipments = shipmentsRepository.save(shipment);

        return mapToResponse(updatedShipments);
    }
    
    // Fetch shipments filtered by customerId
    public List<ShipmentsResponseDto> getShipmentByCustomerId(int customerId) {
    	if (!customersRepository.existsById(customerId)) {
	        throw new ShipmentNotFoundException("Customer not found: " + customerId);
	    }
    	return shipmentsRepository.findByCustomersCustomerId(customerId).stream().map(this::mapToResponse).toList();
    }
    
    // Fetch shipments filtered by storeId
    public List<ShipmentsResponseDto> getShipmentByStoreId(int storeId) {
    	if (!storesRepository.existsById(storeId)) {
            throw new ShipmentNotFoundException("Store not found: " + storeId);
        }
    	return shipmentsRepository.findByStoresStoreId(storeId).stream().map(this::mapToResponse).toList();
    }
    
    // Fetch shipments filtered by shipment status
    public List<ShipmentsResponseDto> getShipmentByStatus(ShipmentStatus status) {
        return shipmentsRepository.findByShipmentStatus(status).stream().map(this::mapToResponse).toList();
    }
    
    // Entity -> DTO conversion
    private ShipmentsResponseDto mapToResponse(Shipments shipment) {

        ShipmentsResponseDto dto = new ShipmentsResponseDto();
        dto.setShipmentId(shipment.getShipmentId());
        dto.setCustomerId(shipment.getCustomers().getCustomerId());
        dto.setStoreId(shipment.getStores().getStoreId());
        dto.setDeliveryAddress(shipment.getDeliveryAddress());
        dto.setShipmentStatus(shipment.getShipmentStatus());

        return dto;
    }
}

