package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.sprintProject.orderinventoryapplication.customexception.InventoryNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.ProductNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.StoreNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Inventory;
import com.sprintProject.orderinventoryapplication.entity.Products;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.InventoryRepository;
import com.sprintProject.orderinventoryapplication.repository.ProductsRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
import com.sprintProject.orderinventoryapplication.service.InventoryService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.InventoryRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.InventoryResponseDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class InventoryTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private StoresRepository storesRepository;

    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory inventory;
    private Stores store;
    private Products product;
    private InventoryRequestDto requestDto;

    @BeforeEach
    void setUp() {
        store = new Stores();
        product = new Products();

        inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setStores(store);
        inventory.setProducts(product);
        inventory.setProductInventory(100);

        requestDto = new InventoryRequestDto();
        requestDto.setProductInventory(200);
    }

    // 1 Positive
    @Test
    void testGetAllInventory() {
        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory));

        assertEquals(1, inventoryService.getAllInventory().size());
    }

    // 2 Positive
    @Test
    void testGetAllInventoryEmpty() {
        when(inventoryRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(0, inventoryService.getAllInventory().size());
    }

    // 3 Positive
    @Test
    void testGetInventoryById() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        InventoryResponseDto dto = inventoryService.getInventoryById(1);

        assertEquals(1, dto.getInventoryId());
    }

    // 4 Negative
    @Test
    void testGetInventoryByIdNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.getInventoryById(1));
    }

    // 5 Positive
    @Test
    void testCreateInventory() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        InventoryResponseDto dto =
                inventoryService.createInventory(1, 1, requestDto);

        assertNotNull(dto);
    }

    // 6 Negative
    @Test
    void testCreateInventoryStoreNotFound() {
        when(storesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    // 7 Negative
    @Test
    void testCreateInventoryProductNotFound() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    // 8 Positive
    @Test
    void testUpdateInventory() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        InventoryResponseDto dto =
                inventoryService.updateInventory(1, 1, 1, requestDto);

        assertNotNull(dto);
    }

    // 9 Negative
    @Test
    void testUpdateInventoryNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.updateInventory(1, 1, 1, requestDto));
    }

    // 10 Negative
    @Test
    void testUpdateInventoryStoreNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(storesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> inventoryService.updateInventory(1, 1, 1, requestDto));
    }

    // 11 Negative
    @Test
    void testUpdateInventoryProductNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> inventoryService.updateInventory(1, 1, 1, requestDto));
    }

    // 12 Positive
    @Test
    void testDeleteInventory() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        String msg = inventoryService.deleteInventory(1);

        assertEquals("Inventory deleted successfully with id : 1", msg);
    }

    // 13 Negative
    @Test
    void testDeleteInventoryNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.deleteInventory(1));
    }

    // 14 Verify save called
    @Test
    void testCreateInventorySaveCalled() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        inventoryService.createInventory(1, 1, requestDto);

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    // 15 Verify delete called
    @Test
    void testDeleteInventoryVerifyDelete() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory(1);

        verify(inventoryRepository, times(1)).delete(inventory);
    }
}

