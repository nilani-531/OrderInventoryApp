package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.sprintProject.orderinventoryapplication.customexception.*;
import com.sprintProject.orderinventoryapplication.entity.*;
import com.sprintProject.orderinventoryapplication.repository.*;
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
        store.setStoreId(1);

        product = new Products();
        product.setProductId(1);

        inventory = new Inventory();
        inventory.setInventoryId(1);
        inventory.setStores(store);
        inventory.setProducts(product);
        inventory.setProductInventory(100);

        requestDto = new InventoryRequestDto();
        requestDto.setProductInventory(200);
    }

    // =====================================================
    // ✅ POSITIVE TEST CASES
    // =====================================================

    @Test
    void testGetAllInventory() {
        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));
        assertEquals(1, inventoryService.getAllInventory().size());
    }

    @Test
    void testGetInventoryById() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        InventoryResponseDto dto = inventoryService.getInventoryById(1);
        assertEquals(1, dto.getInventoryId());
    }

    @Test
    void testCreateInventory() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(new ArrayList<>());
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponseDto dto = inventoryService.createInventory(1, 1, requestDto);
        assertNotNull(dto);
    }

    @Test
    void testUpdateInventory() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        InventoryResponseDto dto = inventoryService.updateInventory(1, 1, 1, requestDto);
        assertNotNull(dto);
    }

    @Test
    void testDeleteInventory() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));
        String msg = inventoryService.deleteInventory(1);
        assertEquals("Inventory deleted successfully with id : 1", msg);
    }

    @Test
    void testGetInventoryByStore() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(List.of(inventory));

        assertFalse(inventoryService.getInventoryByStore(1).isEmpty());
    }

    @Test
    void testGetInventoryByProduct() {
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByProductsProductId(1)).thenReturn(List.of(inventory));

        assertFalse(inventoryService.getInventoryByProduct(1).isEmpty());
    }

    // =====================================================
    // ❌ NEGATIVE TEST CASES
    // =====================================================

    @Test
    void testGetInventoryByIdNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.getInventoryById(1));
    }

    @Test
    void testCreateInventoryNegativeStock() {
        requestDto.setProductInventory(-10);

        assertThrows(InvalidDataException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    @Test
    void testCreateInventoryStoreNotFound() {
        when(storesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    @Test
    void testCreateInventoryProductNotFound() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    @Test
    void testCreateInventoryDuplicate() {
        Inventory existing = new Inventory();
        existing.setProducts(product);

        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(List.of(existing));

        assertThrows(InvalidDataException.class,
                () -> inventoryService.createInventory(1, 1, requestDto));
    }

    @Test
    void testUpdateInventoryNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.updateInventory(1, 1, 1, requestDto));
    }

    @Test
    void testUpdateInventoryNegativeStock() {
        requestDto.setProductInventory(-5);

        assertThrows(InvalidDataException.class,
                () -> inventoryService.updateInventory(1, 1, 1, requestDto));
    }

    @Test
    void testDeleteInventoryNotFound() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class,
                () -> inventoryService.deleteInventory(1));
    }

    @Test
    void testGetInventoryByStoreNotFound() {
        when(storesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class,
                () -> inventoryService.getInventoryByStore(1));
    }

    @Test
    void testGetInventoryByProductNotFound() {
        when(productsRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> inventoryService.getInventoryByProduct(1));
    }

    // =====================================================
    // 🔍 VERIFY INTERACTIONS
    // =====================================================

    @Test
    void testCreateInventoryVerifySave() {
        when(storesRepository.findById(1)).thenReturn(Optional.of(store));
        when(productsRepository.findById(1)).thenReturn(Optional.of(product));
        when(inventoryRepository.findByStoresStoreId(1)).thenReturn(new ArrayList<>());
        when(inventoryRepository.save(any())).thenReturn(inventory);

        inventoryService.createInventory(1, 1, requestDto);

        verify(inventoryRepository, times(1)).save(any());
    }

    @Test
    void testDeleteInventoryVerifyDelete() {
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        inventoryService.deleteInventory(1);

        verify(inventoryRepository, times(1)).delete(inventory);
    }
}