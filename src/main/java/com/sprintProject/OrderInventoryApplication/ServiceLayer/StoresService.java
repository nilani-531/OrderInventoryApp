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

    @Override
    public StoresResponseDto createStore(StoresRequestDto dto) {

        if (repository.existsByStoreName(dto.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        if (dto.getStoreName() == null || dto.getStoreName().isBlank()) {
            throw new InvalidDataException("Store name cannot be empty");
        }

        Stores store = mapToEntity(dto);

        Stores saved = repository.save(store);

        return mapToResponseDto(saved);
    }

    @Override
    public StoresResponseDto getStoreById(int storeId) {
        Stores store = repository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        return mapToResponseDto(store);
    }


    @Override
    public List<StoresResponseDto> getAllStores() {

        List<StoresResponseDto> list = new ArrayList<>();

        List<Stores> stores = repository.findAll();

        for (Stores store : stores) {
            list.add(mapToResponseDto(store));
        }

        return list;
    }

    @Override
    public StoresResponseDto updateStore(int storeId, StoresRequestDto dto) {

        Stores store = repository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        if (dto.getStoreName() == null || dto.getStoreName().isBlank()) {
            throw new InvalidDataException("Store name cannot be empty");
        }

        store.setStoreName(dto.getStoreName());
        store.setWebAddress(dto.getWebAddress());
        store.setPhysicalAddress(dto.getPhysicalAddress());
        store.setLatitude(dto.getLatitude());
        store.setLongitude(dto.getLongitude());

        Stores updated = repository.save(store);

        return mapToResponseDto(updated);
    }

    @Override
    public void deleteStore(int storeId) {
        if (!repository.existsById(storeId)) {
            throw new ResourceNotFoundException("Store not found");
        }
        repository.deleteById(storeId);
    }

    // MAPPING METHODS

    private Stores mapToEntity(StoresRequestDto dto) {
        Stores store = new Stores();
        store.setStoreName(dto.getStoreName());
        store.setWebAddress(dto.getWebAddress());
        store.setPhysicalAddress(dto.getPhysicalAddress());
        store.setLatitude(dto.getLatitude());
        store.setLongitude(dto.getLongitude());
        return store;
    }

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