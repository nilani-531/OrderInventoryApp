package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.StoresService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/stores")



public class StoresController {

    @Autowired
    private StoresService service;

    @PostMapping
    public ResponseEntity<StoresResponseDto> createStore(@RequestBody StoresRequestDto dto) {
        return ResponseEntity.status(201).body(service.createStore(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoresResponseDto> getStore(@PathVariable int id) {
        return ResponseEntity.ok(service.getStoreById(id));
    }

    @GetMapping
    public ResponseEntity<List<StoresResponseDto>> getAllStores() {
        return ResponseEntity.ok(service.getAllStores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoresResponseDto> updateStore(
            @PathVariable int id,
            @RequestBody StoresRequestDto dto) {

        return ResponseEntity.ok(service.updateStore(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStore(@PathVariable int id) {
        service.deleteStore(id);
        return ResponseEntity.ok("Store deleted successfully");
    }
}