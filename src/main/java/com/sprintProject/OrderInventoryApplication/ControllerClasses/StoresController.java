package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.StoresService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("stores")
public class StoresController {

    @Autowired
    private StoresService storesService;

    // -------- GET ALL --------
    @GetMapping
    public ResponseEntity<ResponseStructure<List<StoresResponseDto>>> getAllStores() {

        List<StoresResponseDto> list = storesService.getAllStores();

        ResponseStructure<List<StoresResponseDto>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("All stores fetched successfully");
        response.setData(list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------- GET BY ID --------
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<StoresResponseDto>> getStoreById(@PathVariable int id) {

        StoresResponseDto dto = storesService.getStoreById(id);

        ResponseStructure<StoresResponseDto> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Store fetched successfully");
        response.setData(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------- CREATE --------
    @PostMapping
    public ResponseEntity<ResponseStructure<StoresResponseDto>> createStore(
            @RequestBody StoresRequestDto requestDto) {

        StoresResponseDto dto = storesService.createStore(requestDto);

        ResponseStructure<StoresResponseDto> response = new ResponseStructure<>();
        response.setStatus(201);
        response.setMsg("Store created successfully");
        response.setData(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // -------- UPDATE --------
    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<StoresResponseDto>> updateStore(
            @PathVariable int id,
            @RequestBody StoresRequestDto requestDto) {

        StoresResponseDto dto = storesService.updateStore(id, requestDto);

        ResponseStructure<StoresResponseDto> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Store updated successfully");
        response.setData(dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------- DELETE --------
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteStore(@PathVariable int id) {

        storesService.deleteStore(id);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Store deleted successfully");
        response.setData("Deleted store with id: " + id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}