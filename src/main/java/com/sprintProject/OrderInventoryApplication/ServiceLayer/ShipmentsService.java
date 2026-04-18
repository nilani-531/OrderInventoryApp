package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.InvalidStatusTransitionException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ShipmentNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ShipmentsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;


@Service
public class ShipmentsService implements ShipmentsServiceInterface{
	@Autowired
	private ShipmentsRepository shipmentsRepository;
	
	 @Autowired
	 private CustomersRepository customersRepository;
	 
	 @Autowired
	 private StoresRepository storesRepository;


	public List<ShipmentsResponseDto> getAllShipments(){
		return shipmentsRepository.findAll().stream().map(this::mapToResponse).toList();
	}
    
    public ShipmentsResponseDto getShipmentById(int shipmentId) {
    	
        Shipments shipment= shipmentsRepository.findById(shipmentId)
        		.orElseThrow(()-> new ShipmentNotFoundException("Shipment not found: "+shipmentId));
        
        return mapToResponse(shipment);
    }
    
    public ShipmentsResponseDto createShipment(ShipmentsRequestDto shipmentsRequestDto) {
    	
    	Customers customer = customersRepository.findById(shipmentsRequestDto.getCustomerId())
                .orElseThrow(() -> new ShipmentNotFoundException(
                        "Customer not found: " + shipmentsRequestDto.getCustomerId()));

        Stores store = storesRepository.findById(shipmentsRequestDto.getStoreId())
                .orElseThrow(() -> new ShipmentNotFoundException(
                        "Store not found: " + shipmentsRequestDto.getStoreId()));

        Shipments shipment = new Shipments();
        shipment.setCustomers(customer);
        shipment.setStores(store);
        shipment.setDeliveryAddress(shipmentsRequestDto.getDeliveryAddress());
        shipment.setShipmentStatus(ShipmentStatus.CREATED);
        Shipments savedShipment = shipmentsRepository.save(shipment);
        return mapToResponse(savedShipment);
    }
   
    public ShipmentsResponseDto updateShipment(int shipmentId, ShipmentsRequestDto request) {
    	
    	Shipments existingShipment = shipmentsRepository.findById(shipmentId)
                .orElseThrow(() -> new ShipmentNotFoundException(
                        "Shipment not found: " + shipmentId));

        if (request.getDeliveryAddress() != null) {
        	existingShipment.setDeliveryAddress(request.getDeliveryAddress());
        }

        if (request.getCustomerId() != 0) {
            Customers customer = customersRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new ShipmentNotFoundException(
                            "Customer not found: " + request.getCustomerId()));
            existingShipment.setCustomers(customer);
        }

        if (request.getStoreId() != 0) {
            Stores store = storesRepository.findById(request.getStoreId())
                    .orElseThrow(() -> new ShipmentNotFoundException(
                            "Store not found: " + request.getStoreId()));
            existingShipment.setStores(store);
        }

        return mapToResponse(shipmentsRepository.save(existingShipment));
    }
    
    public String deleteShipment(int shipmentId) {

        if (!shipmentsRepository.existsById(shipmentId)) {
            throw new ShipmentNotFoundException("Shipment not found: " + shipmentId);
        }

        shipmentsRepository.deleteById(shipmentId);

        return "Shipment deleted successfully with id: " + shipmentId;
    }
    
    public List<Shipments> getShipmentByCustomerId(int customerId) {
        return shipmentsRepository.findByCustomersCustomerId(customerId);
    }
    
    public List<Shipments> getShipmentByStoreId(int storeId) {
        return shipmentsRepository.findByStoresStoreId(storeId);
    }
    
    public List<Shipments> getShipmentByStatus(ShipmentStatus status) {
        return shipmentsRepository.findByShipmentStatus(status);
    }
    
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
