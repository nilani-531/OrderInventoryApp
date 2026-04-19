package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

import java.util.List;

public interface StoresServiceInterface {

    StoresResponseDto createStore(StoresRequestDto dto);

    StoresResponseDto getStoreById(int storeId);

    List<StoresResponseDto> getAllStores();

    StoresResponseDto updateStore(int storeId, StoresRequestDto dto);

    void deleteStore(int storeId);
}