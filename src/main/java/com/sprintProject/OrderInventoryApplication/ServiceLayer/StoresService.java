package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.*;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StoresService {

    @Autowired
    private StoresRepository storeRepository;

    // -------- GET ALL --------
    public List<StoresResponseDto> getAllStores() {
        return storeRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    // -------- GET BY ID --------
    public StoresResponseDto getStoreById(int id) {
        Stores store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + id));

        return mapToResponseDto(store);
    }

    // -------- CREATE --------
    public StoresResponseDto createStore(StoresRequestDto dto) {

        if (storeRepository.existsByStoreName(dto.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        if ((dto.getWebAddress() == null || dto.getWebAddress().isEmpty()) &&
                (dto.getPhysicalAddress() == null || dto.getPhysicalAddress().isEmpty())) {
            throw new InvalidDataException("Either web or physical address required");
        }

        Stores store = mapToEntity(dto);
        store.setLogoLastUpdated(LocalDate.now());

        Stores saved = storeRepository.save(store);

        return mapToResponseDto(saved);
    }

    // -------- UPDATE --------
    public StoresResponseDto updateStore(int id, StoresRequestDto dto) {

        Stores existing = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        if (!existing.getStoreName().equals(dto.getStoreName()) &&
                storeRepository.existsByStoreName(dto.getStoreName())) {
            throw new DuplicateResourceException("Store name already exists");
        }

        if ((dto.getWebAddress() == null || dto.getWebAddress().isEmpty()) &&
                (dto.getPhysicalAddress() == null || dto.getPhysicalAddress().isEmpty())) {
            throw new InvalidDataException("Either web or physical address required");
        }

        existing.setStoreName(dto.getStoreName());
        existing.setWebAddress(dto.getWebAddress());
        existing.setPhysicalAddress(dto.getPhysicalAddress());
        existing.setLatitude(dto.getLatitude());
        existing.setLongitude(dto.getLongitude());

        if (dto.getLogo() != null) {
            existing.setLogo(dto.getLogo());
            existing.setLogoMimeType(dto.getLogoMimeType());
            existing.setLogoFilename(dto.getLogoFilename());
            existing.setLogoCharset(dto.getLogoCharset());
            existing.setLogoLastUpdated(LocalDate.now());
        }

        return mapToResponseDto(storeRepository.save(existing));
    }

    // -------- DELETE --------
    public void deleteStore(int id) {
        Stores store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        storeRepository.delete(store);
    }

    // -------- MAPPERS --------
    private StoresResponseDto mapToResponseDto(Stores store) {
        StoresResponseDto dto = new StoresResponseDto();

        dto.setStoreId(store.getStoreId());
        dto.setStoreName(store.getStoreName());
        dto.setWebAddress(store.getWebAddress());
        dto.setPhysicalAddress(store.getPhysicalAddress());
        dto.setLatitude(store.getLatitude());
        dto.setLongitude(store.getLongitude());
        dto.setLogo(store.getLogo());
        dto.setLogoMimeType(store.getLogoMimeType());
        dto.setLogoFilename(store.getLogoFilename());
        dto.setLogoCharset(store.getLogoCharset());
        dto.setLogoLastUpdated(store.getLogoLastUpdated());

        return dto;
    }

    private Stores mapToEntity(StoresRequestDto dto) {
        Stores store = new Stores();

        store.setStoreName(dto.getStoreName());
        store.setWebAddress(dto.getWebAddress());
        store.setPhysicalAddress(dto.getPhysicalAddress());
        store.setLatitude(dto.getLatitude());
        store.setLongitude(dto.getLongitude());
        store.setLogo(dto.getLogo());
        store.setLogoMimeType(dto.getLogoMimeType());
        store.setLogoFilename(dto.getLogoFilename());
        store.setLogoCharset(dto.getLogoCharset());

        return store;
    }
}