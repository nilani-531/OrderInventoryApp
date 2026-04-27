package com.sprintProject.orderinventoryapplication.testservice;

import com.sprintProject.orderinventoryapplication.customexception.*;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
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
class StoresServiceTest {

    // Mocking repository (we don't hit real DB in unit tests)
    @Mock
    private StoresRepository repository;

    // Injecting mock repository into service
    @InjectMocks
    private StoresService service;

    private StoresRequestDto dto;
    private Stores store;

    // Common test data setup before each test
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


    // Test creating a store successfully
    @Test
    void createStore_success() {
        // Arrange: store does NOT exist
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(false);
        when(repository.save(any())).thenReturn(store);

        // Act
        StoresResponseDto res = service.createStore(dto);

        // Assert
        assertNotNull(res);
        assertEquals("Test Store", res.getStoreName());
    }

    // Test fetching store by name successfully
    @Test
    void getStoreByName_success() {

        // Arrange: mock repository to return a store for given name
        when(repository.findStoreByName("ABC Store")).thenReturn(store);

        // Act: call service method
        StoresResponseDto res = service.getStoreByName("ABC Store");

        // Assert: verify response is correct
        assertNotNull(res);
        assertEquals("ABC Store", res.getStoreName());
    }

    @Test
    void createStore_duplicateStore_shouldThrowException() {
        // Arrange: store already exists
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class,
                () -> service.createStore(dto));
    }

    // Test creating store with null name should throw exception
    @Test
    void createStore_nullStoreName_shouldThrowException() {
        // Arrange: invalid input (null name)
        dto.setStoreName(null);

        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> service.createStore(dto));
    }

    // Test creating store with blank name should throw exception
    @Test
    void createStore_blankStoreName_shouldThrowException() {
        // Arrange: invalid input (blank name)
        dto.setStoreName("   ");

        // Act & Assert
        assertThrows(InvalidDataException.class,
                () -> service.createStore(dto));
    }

    // Test getting store by ID successfully
    @Test
    void getStoreById_success() {
        when(repository.findById(1)).thenReturn(Optional.of(store));

        StoresResponseDto res = service.getStoreById(1);

        assertEquals(1, res.getStoreId());
    }

    // Test getting store by ID when not found should throw exception        assertEquals(1, res.getStoreId());
     @Test
    void getStoreById_notFound_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> service.getStoreById(1));
    }

    // Test getting all stores successfully
    @Test
    void getAllStores_success() {
        when(repository.findAll()).thenReturn(List.of(store));

        List<StoresResponseDto> res = service.getAllStores();

        assertEquals(1, res.size());
    }

    // Test getting all stores returns empty list        assertEquals(1, res.size());
      @Test
    void getAllStores_emptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<StoresResponseDto> res = service.getAllStores();

        assertTrue(res.isEmpty());
    }

    // Test updating store successfully
    @Test
    void updateStore_success() {
        when(repository.findById(1)).thenReturn(Optional.of(store));
        when(repository.save(any())).thenReturn(store);

        StoresResponseDto res = service.updateStore(1, dto);

        assertEquals("Test Store", res.getStoreName());
    }

    // Test updating store when not found should throw exception
    @Test
    void updateStore_storeNotFound_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> service.updateStore(1, dto));
    }

    // Test updating store with null name should throw exception
    @Test
    void updateStore_nullName_shouldThrowException() {
        dto.setStoreName(null);
        when(repository.findById(1)).thenReturn(Optional.of(store));

        assertThrows(InvalidDataException.class,
                () -> service.updateStore(1, dto));
    }

    // Test updating store with blank name should throw exception                () -> service.updateStore(1, dto));
    

    @Test
    void updateStore_blankName_shouldThrowException() {
        dto.setStoreName(" ");
        when(repository.findById(1)).thenReturn(Optional.of(store));
    }

    // Test deleting store successfully
    @Test
    void deleteStore_success() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        assertDoesNotThrow(() -> service.deleteStore(1));
    }

    // Test deleting store when not found should throw exception
    @Test
    void deleteStore_notFound_shouldThrowException() {
        when(repository.existsById(1)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteStore(1));
    }

    // Test repository save failure should throw exception
    @Test
    void repositorySaveFailure_shouldThrowException() {
        when(repository.existsByStoreName(dto.getStoreName())).thenReturn(false);
        when(repository.save(any())).thenThrow(new RuntimeException("DB failure"));

        assertThrows(RuntimeException.class,
                () -> service.createStore(dto));
    

        when(repository.save(any())).thenThrow(new RuntimeException("DB failure"));

        assertThrows(RuntimeException.class,
                () -> service.createStore(dto));
    }

    // Test repository update failure should throw exception
    @Test
    void updateRepositoryFailure_shouldThrowException() {
        when(repository.findById(1)).thenReturn(Optional.of(store));
        when(repository.save(any())).thenThrow(new RuntimeException("DB failure"));

        assertThrows(RuntimeException.class,
                () -> service.updateStore(1, dto));
    }
}

