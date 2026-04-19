package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.StoresService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

@RestController
@RequestMapping("/stores")
public class StoresController {

    @Autowired
    private StoresService service;

    // Create Store
    @PostMapping
    public ResponseStructure<StoresResponseDto> createStore(@RequestBody StoresRequestDto dto) {
        StoresResponseDto response = service.createStore(dto);

        ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Store created successfully");
        rs.setData(response);

        return rs;
    }

    // Get Store by ID
    @GetMapping("/{id}")
    public ResponseStructure<StoresResponseDto> getStore(@PathVariable int id) {
        StoresResponseDto response = service.getStoreById(id);

        ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Store fetched successfully");
        rs.setData(response);

        return rs;
    }

    // Get Store by Name
    @GetMapping("/name/{name}")
    public ResponseStructure<StoresResponseDto> getStoreByName(@PathVariable String name) {
        StoresResponseDto response = service.getStoreByName(name);

        ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Store fetched successfully");
        rs.setData(response);

        return rs;
    }

    // Get All Stores
    @GetMapping
    public ResponseStructure<List<StoresResponseDto>> getAllStores() {
        List<StoresResponseDto> list = service.getAllStores();

        ResponseStructure<List<StoresResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Stores fetched successfully");
        rs.setData(list);

        return rs;
    }

    // Update Store
    @PutMapping("/{id}")
    public ResponseStructure<StoresResponseDto> updateStore(
            @PathVariable int id,
            @RequestBody StoresRequestDto dto) {

        StoresResponseDto response = service.updateStore(id, dto);

        ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Store updated successfully");
        rs.setData(response);

        return rs;
    }

    // Delete Store
    @DeleteMapping("/{id}")
    public ResponseStructure<String> deleteStore(@PathVariable int id) {
        service.deleteStore(id);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Store deleted successfully");
        rs.setData("Store deleted successfully with id: " + id);

        return rs;
    }
}