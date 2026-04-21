package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.InvalidDataException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.StoreNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ResourceNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.DuplicateResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoresService implements StoresServiceInterface{

    @Autowired
    private StoresRepository repository;

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
    @Override
    public void deleteStore(int storeId) {

        // Check if store exists
        if (!repository.existsById(storeId)) {
            throw new ResourceNotFoundException("Store not found");
        }

        // Delete store
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