package com.sprintProject.orderinventoryapplication.service;

import com.sprintProject.orderinventoryapplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.StoresResponseDto;

import java.util.List;

public interface StoresServiceInterface {

    // Creates a new store using the given request data
    StoresResponseDto createStore(StoresRequestDto dto);

    // Retrieves a store based on its unique ID
    StoresResponseDto getStoreById(int storeId);

    // Retrieves a store based on its name
    StoresResponseDto getStoreByName(String storeName);

    // Fetches all available stores as a list
    List<StoresResponseDto> getAllStores();

    // Updates an existing store with new data based on ID
    StoresResponseDto updateStore(int storeId, StoresRequestDto dto);

    // Deletes a store using its ID
    void deleteStore(int storeId);
}

