package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sprintProject.orderinventoryapplication.customexception.InvalidStatusTransitionException;
import com.sprintProject.orderinventoryapplication.customexception.ShipmentNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Customers;
import com.sprintProject.orderinventoryapplication.entity.ShipmentStatus;
import com.sprintProject.orderinventoryapplication.entity.Shipments;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository;
import com.sprintProject.orderinventoryapplication.repository.ShipmentsRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
import com.sprintProject.orderinventoryapplication.service.ShipmentsService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ShipmentsResponseDto;

@ExtendWith(MockitoExtension.class)
public class ShipmentsTest {
	
	@InjectMocks
    private ShipmentsService service;

    @Mock
    private ShipmentsRepository shipmentsRepository;

    @Mock
    private CustomersRepository customersRepository;

    @Mock
    private StoresRepository storesRepository;

    private Shipments shipments;
    private Customers customers;
    private Stores stores;

    @BeforeEach
    void setup() {

        // creating sample customer for tests
        customers = new Customers();
        customers.setCustomerId(1);

        // creating sample store for tests
        stores = new Stores();
        stores.setStoreId(1);

        // sample shipment data used in multiple tests
        shipments = new Shipments();
        shipments.setShipmentId(1);
        shipments.setCustomers(customers);
        shipments.setStores(stores);
        shipments.setDeliveryAddress("Chennai");
        shipments.setShipmentStatus(ShipmentStatus.CREATED);
    }

    // CREATE SHIPMENT
    // positive case: shipment created successfully when customer and store exist
    @Test
    void createShipmentSuccess() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);
        dto.setDeliveryAddress("Chennai");
        when(customersRepository.findById(1)).thenReturn(Optional.of(customers));
        when(storesRepository.findById(1)).thenReturn(Optional.of(stores));
        when(shipmentsRepository.save(any())).thenReturn(shipments);
        ShipmentsResponseDto result = service.createShipment(dto);
        assertNotNull(result);
        assertEquals("Chennai", result.getDeliveryAddress());
    }

    // negative case: customer not found while creating shipment
    @Test
    void createShipmentCustomerNotFound() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setCustomerId(99);
        when(customersRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ShipmentNotFoundException.class, () -> service.createShipment(dto));
    }

    // negative case: store not found while creating shipment
    @Test
    void createShipmentStoreNotFound() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(99);
        when(customersRepository.findById(1)).thenReturn(Optional.of(customers));
        when(storesRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ShipmentNotFoundException.class, () -> service.createShipment(dto));
    }

    // GET SHIPMENT BY ID 
    // positive case: shipment found by id
    @Test
    void getShipmentByIdSuccess() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        ShipmentsResponseDto result = service.getShipmentById(1);
        assertEquals(1, result.getShipmentId());
    }

    // negative case: shipment id does not exist
    @Test
    void getShipmentByIdNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ShipmentNotFoundException.class, () -> service.getShipmentById(1));
    }

    // UPDATE SHIPMENT 
    // positive case: update shipment delivery address successfully
    @Test
    void updateShipmentSuccess() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setDeliveryAddress("Updated");
        Shipments updated = new Shipments();
        updated.setShipmentId(1);
        updated.setCustomers(customers);
        updated.setStores(stores);
        updated.setDeliveryAddress("Updated");
        updated.setShipmentStatus(ShipmentStatus.CREATED);
        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        when(shipmentsRepository.save(any())).thenReturn(updated);
        ShipmentsResponseDto result = service.updateShipment(1, dto);
        assertEquals("Updated", result.getDeliveryAddress());
    }

    // negative case: updating shipment that doesn't exist
    @Test
    void updateShipmentNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ShipmentNotFoundException.class, () -> service.updateShipment(1, new ShipmentsRequestDto()));
    }

    // DELETE SHIPMENT 
    // positive case: shipment deleted successfully
    @Test
    void deleteShipmentSuccess() {

        when(shipmentsRepository.existsById(1)).thenReturn(true);
        String result = service.deleteShipment(1);
        assertNotNull(result);
    }

    // negative case: trying to delete non-existing shipment
    @Test
    void deleteShipmentNotFound() {

        when(shipmentsRepository.existsById(1)).thenReturn(false);
        assertThrows(ShipmentNotFoundException.class, () -> service.deleteShipment(1));
    }

    // STATUS UPDATE
    // positive case: valid status change from CREATED to SHIPPED
    @Test
    void validStatusTransition() {

    	shipments.setShipmentStatus(ShipmentStatus.CREATED);
        Shipments updated = new Shipments();
        updated.setShipmentId(1);
        updated.setCustomers(customers);   
        updated.setStores(stores);        
        updated.setDeliveryAddress("Chennai");
        updated.setShipmentStatus(ShipmentStatus.SHIPPED);
        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        when(shipmentsRepository.save(any())).thenReturn(updated);
        ShipmentsResponseDto result = service.updateShipmentStatus(1, ShipmentStatus.SHIPPED);
        assertEquals(ShipmentStatus.SHIPPED, result.getShipmentStatus());
    }

    // negative case: invalid status change not allowed
    @Test
    void invalidStatusTransition() {

        shipments.setShipmentStatus(ShipmentStatus.CREATED);
        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        assertThrows(InvalidStatusTransitionException.class, () -> service.updateShipmentStatus(1, ShipmentStatus.DELIVERED));
    }

    // negative case: status update for non-existing shipment
    @Test
    void updateStatusShipmentNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ShipmentNotFoundException.class, () -> service.updateShipmentStatus(1, ShipmentStatus.SHIPPED));
    }

    // FETCHING DATA 
    // positive case: fetch all shipments
    @Test
    void getAllShipments() {

        when(shipmentsRepository.findAll()).thenReturn(List.of(shipments));
        List<ShipmentsResponseDto> result = service.getAllShipments();
        assertFalse(result.isEmpty());
    }

    // positive case: fetch shipments by customer id
    @Test
    void getShipmentByCustomerId() {

        when(shipmentsRepository.findByCustomersCustomerId(1)).thenReturn(List.of(shipments));
        List<ShipmentsResponseDto> result = service.getShipmentByCustomerId(1);
        assertEquals(1, result.size());
    }

    // positive case: fetch shipments by status
    @Test
    void getShipmentByStatus() {

        when(shipmentsRepository.findByShipmentStatus(ShipmentStatus.CREATED)).thenReturn(List.of(shipments));
        List<ShipmentsResponseDto> result = service.getShipmentByStatus(ShipmentStatus.CREATED);
        assertEquals(1, result.size());
    }
}


