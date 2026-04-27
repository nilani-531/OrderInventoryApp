package com.sprintProject.orderinventoryapplication.service;

import com.sprintProject.orderinventoryapplication.customexception.InvalidDataException;
import com.sprintProject.orderinventoryapplication.customexception.StoreNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Inventory;
import com.sprintProject.orderinventoryapplication.entity.Orders;
import com.sprintProject.orderinventoryapplication.entity.OrderItems;
import com.sprintProject.orderinventoryapplication.entity.Shipments;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.InventoryRepository;
import com.sprintProject.orderinventoryapplication.repository.OrderItemsRepository;
import com.sprintProject.orderinventoryapplication.repository.OrdersRepository;
import com.sprintProject.orderinventoryapplication.repository.ShipmentsRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
import com.sprintProject.orderinventoryapplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.StoresResponseDto;
import com.sprintProject.orderinventoryapplication.customexception.ResourceNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.DuplicateResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoresService implements StoresServiceInterface{

    @Autowired
    private StoresRepository repository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private ShipmentsRepository shipmentsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // Create a new store after validating input and checking duplicates
    @Override
    public StoresResponseDto createStore(StoresRequestDto dto) {

        // Validate store name input
        if (dto.getStoreName() == null || dto.getStoreName().isBlank()) {
            throw new InvalidDataException("Store name cannot be empty");
        }

        // Check for duplicate store name
        if (repository.existsByStoreName(dto.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        // Convert DTO to Entity
        Stores store = mapToEntity(dto);

        // Save to database
        Stores saved = repository.save(store);

        // Return response DTO
        return mapToResponseDto(saved);
    }

    // Fetch a store by its ID, throw exception if not found
    @Override
    public StoresResponseDto getStoreById(int storeId) {

        // Retrieve store or throw exception
        Stores store = repository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        return mapToResponseDto(store);
    }

    // Fetch a store by its name using custom query
    @Override
    public StoresResponseDto getStoreByName(String storeName) {

        Stores store = repository.findStoreByName(storeName);

        if (store == null) {
            throw new StoreNotFoundException("Store not found");
        }

        return mapToResponseDto(store);
    }

    // Retrieve all stores and convert them into response DTO list
    @Override
    public List<StoresResponseDto> getAllStores() {

        // List to hold response DTOs
        List<StoresResponseDto> list = new ArrayList<>();

        // Fetch all store entities
        List<Stores> stores = repository.findAll();

        // Convert each entity to DTO
        for (Stores store : stores) {
            list.add(mapToResponseDto(store));
        }

        return list;
    }

    // Update existing store details after validation
    @Override
    public StoresResponseDto updateStore(int storeId, StoresRequestDto dto) {

        // Fetch existing store
        Stores store = repository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        // Validate input
        if (dto.getStoreName() == null || dto.getStoreName().isBlank()) {
            throw new InvalidDataException("Store name cannot be empty");
        }

        // Prevent duplicate store name during update
        if (repository.existsByStoreName(dto.getStoreName())
                && !store.getStoreName().equals(dto.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        // Update store fields
        store.setStoreName(dto.getStoreName());
        store.setWebAddress(dto.getWebAddress());
        store.setPhysicalAddress(dto.getPhysicalAddress());
        store.setLatitude(dto.getLatitude());
        store.setLongitude(dto.getLongitude());

        // Save updated entity
        Stores updated = repository.save(store);

        return mapToResponseDto(updated);
    }

    // Delete a store by ID after checking existence
    // Cascade order: inventory → shipments → order_items → orders → store
    @Override
    @Transactional
    public void deleteStore(int storeId) {

        // Check if store exists
        if (!repository.existsById(storeId)) {
            throw new ResourceNotFoundException("Store not found");
        }

        // 1. Delete inventory records referencing this store
        List<Inventory> inventoryRecords = inventoryRepository.findByStoresStoreId(storeId);
        inventoryRepository.deleteAll(inventoryRecords);

        // 2. Delete shipments referencing this store
        List<Shipments> shipments = shipmentsRepository.findByStoresStoreId(storeId);
        shipmentsRepository.deleteAll(shipments);

        // 3. Delete order items then orders referencing this store
        List<Orders> storeOrders = ordersRepository.findByStoreId(storeId);
        for (Orders order : storeOrders) {
            List<OrderItems> items = orderItemsRepository.findByOrderId(order.getOrderId());
            orderItemsRepository.deleteAll(items);
            ordersRepository.delete(order);
        }

        // 4. Now safe to delete the store
        repository.deleteById(storeId);
    }

    // Convert request DTO to entity object
    private Stores mapToEntity(StoresRequestDto dto) {
        Stores store = new Stores();
        store.setStoreName(dto.getStoreName());
        store.setWebAddress(dto.getWebAddress());
        store.setPhysicalAddress(dto.getPhysicalAddress());
        store.setLatitude(dto.getLatitude());
        store.setLongitude(dto.getLongitude());
        return store;
    }

    // Convert entity object to response DTO
    private StoresResponseDto mapToResponseDto(Stores store) {
        StoresResponseDto dto = new StoresResponseDto();
        dto.setStoreId(store.getStoreId());
        dto.setStoreName(store.getStoreName());
        dto.setWebAddress(store.getWebAddress());
        dto.setPhysicalAddress(store.getPhysicalAddress());
        dto.setLatitude(store.getLatitude());
        dto.setLongitude(store.getLongitude());
        dto.setLogoFilename(store.getLogoFilename());
        dto.setLogoMimeType(store.getLogoMimeType());
        dto.setLogoLastUpdated(store.getLogoLastUpdated());
        return dto;
    }
}

