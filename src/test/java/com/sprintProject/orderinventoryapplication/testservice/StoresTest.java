package com.sprintProject.orderinventoryapplication.testservice;

import com.sprintProject.orderinventoryapplication.customexception.*;
import com.sprintProject.orderinventoryapplication.entity.*;
import com.sprintProject.orderinventoryapplication.repository.*;
import com.sprintProject.orderinventoryapplication.service.StoresService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.StoresResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoresTest {

    // Main repository
    @Mock
    private StoresRepository repository;

    // Cascade dependencies (IMPORTANT for deleteStore)
    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private ShipmentsRepository shipmentsRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    // Inject all mocks into service
    @InjectMocks
    private StoresService service;

    private StoresRequestDto dto;
    private Stores store;

    // Common test data
    @BeforeEach
    void setup() {
        dto = new StoresRequestDto();
        dto.setStoreName("Test Store");
        dto.setWebAddress("www.test.com");
        dto.setPhysicalAddress("Chennai");
        dto.setLatitude(12.97);
        dto.setLongitude(80.22);

        store = new Stores();
        store.setStoreId(1);
        store.setStoreName("Test Store");
        store.setWebAddress("www.test.com");
        store.setPhysicalAddress("Chennai");
        store.setLatitude(12.97);
        store.setLongitude(80.22);
    }

    // Create store - success
    @Test
    void createStore_success() {
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(false);
        when(repository.save(any())).thenReturn(store);

        StoresResponseDto res = service.createStore(dto);

        assertNotNull(res);
        assertEquals("Test Store", res.getStoreName());
    }

    // Create store - duplicate
    @Test
    void createStore_duplicateStore_shouldThrowException() {
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> service.createStore(dto));
    }

    // Create store - null name
    @Test
    void createStore_nullStoreName_shouldThrowException() {
        dto.setStoreName(null);

        assertThrows(InvalidDataException.class,
                () -> service.createStore(dto));
    }

    // Create store - blank name
    @Test
    void createStore_blankStoreName_shouldThrowException() {
        dto.setStoreName("   ");

        assertThrows(InvalidDataException.class,
                () -> service.createStore(dto));
    }

    // Get store by ID - success
    @Test
    void getStoreById_success() {
        when(repository.findById(1)).thenReturn(Optional.of(store));

        StoresResponseDto res = service.getStoreById(1);

        assertEquals(1, res.getStoreId());
    }

    // Get store by ID - not found
    @Test
    void getStoreById_notFound_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> service.getStoreById(1));
    }

    // Get store by name - success
    @Test
    void getStoreByName_success() {
        when(repository.findStoreByName("Test Store")).thenReturn(store);

        StoresResponseDto res = service.getStoreByName("Test Store");

        assertNotNull(res);
        assertEquals("Test Store", res.getStoreName());
    }

    // Get all stores - non-empty
    @Test
    void getAllStores_success() {
        when(repository.findAll()).thenReturn(List.of(store));

        List<StoresResponseDto> res = service.getAllStores();

        assertEquals(1, res.size());
    }

    // Get all stores - empty
    @Test
    void getAllStores_emptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<StoresResponseDto> res = service.getAllStores();

        assertTrue(res.isEmpty());
    }

    // Update store - success
    @Test
    void updateStore_success() {
        when(repository.findById(1)).thenReturn(Optional.of(store));
        when(repository.save(any())).thenReturn(store);

        StoresResponseDto res = service.updateStore(1, dto);

        assertEquals("Test Store", res.getStoreName());
    }

    // Update store - not found
    @Test
    void updateStore_storeNotFound_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> service.updateStore(1, dto));
    }

    // Update store - null name
    @Test
    void updateStore_nullName_shouldThrowException() {
        dto.setStoreName(null);
        when(repository.findById(1)).thenReturn(Optional.of(store));

        assertThrows(InvalidDataException.class,
                () -> service.updateStore(1, dto));
    }

    // Update store - blank name
    @Test
    void updateStore_blankName_shouldThrowException() {
        dto.setStoreName(" ");
        when(repository.findById(1)).thenReturn(Optional.of(store));

        assertThrows(InvalidDataException.class,
                () -> service.updateStore(1, dto));
    }

    // Delete store - success (with cascade mocks)
    @Test
    void deleteStore_success() {
        when(repository.existsById(1)).thenReturn(true);

        // Mock cascade calls
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(List.of());
        when(shipmentsRepository.findByStoresStoreId(1)).thenReturn(List.of());
        when(ordersRepository.findByStoreId(1)).thenReturn(List.of());

        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deleteStore(1));

        verify(repository, times(1)).deleteById(1);
    }

    // Delete store - not found
    @Test
    void deleteStore_notFound_shouldThrowException() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteStore(1));
    }

    // Repository save failure
    @Test
    void repositorySaveFailure_shouldThrowException() {
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(false);
        when(repository.save(any())).thenThrow(new RuntimeException("DB failure"));

        assertThrows(RuntimeException.class,
                () -> service.createStore(dto));
    }

    // Repository update failure
    @Test
    void updateRepositoryFailure_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.of(store));
        when(repository.save(any())).thenThrow(new RuntimeException("DB failure"));

        assertThrows(RuntimeException.class,
                () -> service.updateStore(1, dto));
    }

    // Advanced: verify cascade delete logic
    @Test
    void deleteStore_withOrders_shouldDeleteOrderItemsAndOrders() {

        Orders order = new Orders();
        order.setOrderId(1);

        when(repository.existsById(1)).thenReturn(true);
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(List.of());
        when(shipmentsRepository.findByStoresStoreId(1)).thenReturn(List.of());
        when(ordersRepository.findByStoreId(1)).thenReturn(List.of(order));
        when(orderItemsRepository.findByOrderId(1)).thenReturn(List.of());

        service.deleteStore(1);

        verify(orderItemsRepository).deleteAll(any());
        verify(ordersRepository).delete(order);
        verify(repository).deleteById(1);
    }
}