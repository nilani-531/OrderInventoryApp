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

    // Injecting mocked dependencies into service
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

    // Setup common test data
    @BeforeEach
    void setup() {

        customers = new Customers();
        customers.setCustomerId(1);

        stores = new Stores();
        stores.setStoreId(1);

        shipments = new Shipments();
        shipments.setShipmentId(1);
        shipments.setCustomers(customers);
        shipments.setStores(stores);
        shipments.setDeliveryAddress("Chennai");
        shipments.setShipmentStatus(ShipmentStatus.CREATED);
    }

    // Test creating shipment successfully
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
        assertEquals(ShipmentStatus.CREATED, result.getShipmentStatus());
    }

    // Test create shipment when customer not found
    @Test
    void createShipmentCustomerNotFound() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setCustomerId(99);

        when(customersRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class,
                () -> service.createShipment(dto));
    }

    // Test fetching shipments by customer id successfully (+ve case)
    @Test
    void getShipmentByCustomerId() {

        when(customersRepository.existsById(1)).thenReturn(true);
        when(shipmentsRepository.findByCustomersCustomerId(1))
                .thenReturn(List.of(shipments));

        List<ShipmentsResponseDto> result =
                service.getShipmentByCustomerId(1);

        assertEquals(1, result.size());
    }
    
    // Test create shipment when store not found
    @Test
    void createShipmentStoreNotFound() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(99);

        when(customersRepository.findById(1)).thenReturn(Optional.of(customers));
        when(storesRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class,
                () -> service.createShipment(dto));
    }

    // Test fetching shipment by id successfully
    @Test
    void getShipmentByIdSuccess() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));

        ShipmentsResponseDto result = service.getShipmentById(1);

        assertEquals(1, result.getShipmentId());
    }

    // Test fetching shipment by id when not found
    @Test
    void getShipmentByIdNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class,
                () -> service.getShipmentById(1));
    }

    // Test updating shipment delivery address
    @Test
    void updateShipmentSuccess() {

        ShipmentsRequestDto dto = new ShipmentsRequestDto();
        dto.setDeliveryAddress("Updated");

        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        when(shipmentsRepository.save(any())).thenReturn(shipments);

        ShipmentsResponseDto result = service.updateShipment(1, dto);

        assertEquals("Updated", result.getDeliveryAddress());
    }

    // Test updating shipment when not found
    @Test
    void updateShipmentNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class,
                () -> service.updateShipment(1, new ShipmentsRequestDto()));
    }

    // Test deleting shipment successfully
    @Test
    void deleteShipmentSuccess() {

        when(shipmentsRepository.existsById(1)).thenReturn(true);

        String result = service.deleteShipment(1);

        assertEquals("Shipment deleted successfully with id: 1", result);
        verify(shipmentsRepository, times(1)).deleteById(1);
    }

    // Test deleting shipment when not found
    @Test
    void deleteShipmentNotFound() {

        when(shipmentsRepository.existsById(1)).thenReturn(false);

        assertThrows(ShipmentNotFoundException.class,
                () -> service.deleteShipment(1));
    }

    // Test valid status transition
    @Test
    void validStatusTransition() {

        shipments.setShipmentStatus(ShipmentStatus.CREATED);

        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));
        when(shipmentsRepository.save(any())).thenReturn(shipments);

        ShipmentsResponseDto result =
                service.updateShipmentStatus(1, ShipmentStatus.SHIPPED);

        assertEquals(ShipmentStatus.SHIPPED, result.getShipmentStatus());
    }

    // Test invalid status transition
    @Test
    void invalidStatusTransition() {

        shipments.setShipmentStatus(ShipmentStatus.CREATED);

        when(shipmentsRepository.findById(1)).thenReturn(Optional.of(shipments));

        assertThrows(InvalidStatusTransitionException.class,
                () -> service.updateShipmentStatus(1, ShipmentStatus.DELIVERED));
    }

    // Test status update when shipment not found
    @Test
    void updateStatusShipmentNotFound() {

        when(shipmentsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ShipmentNotFoundException.class,
                () -> service.updateShipmentStatus(1, ShipmentStatus.SHIPPED));
    }

    // Test fetching all shipments
    @Test
    void getAllShipments() {

        when(shipmentsRepository.findAll()).thenReturn(List.of(shipments));

        List<ShipmentsResponseDto> result = service.getAllShipments();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    // Test fetching shipments by status
    @Test
    void getShipmentByStatus() {

        when(shipmentsRepository.findByShipmentStatus(ShipmentStatus.CREATED))
                .thenReturn(List.of(shipments));

        List<ShipmentsResponseDto> result =
                service.getShipmentByStatus(ShipmentStatus.CREATED);

        assertEquals(1, result.size());
    }
}