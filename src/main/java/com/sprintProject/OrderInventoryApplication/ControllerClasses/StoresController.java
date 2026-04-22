package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.InventoryService;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrdersService;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.StoresService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.StoresRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.StoresResponseDto;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/stores")
public class StoresController {

    @Autowired
    private StoresService service;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrdersService ordersService;

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

    //get inventory by store id
    @GetMapping("/{storeId}/inventory")
    public ResponseStructure<List<Inventory>> getInventory(@PathVariable int storeId) {

        List<Inventory> list = inventoryService.getInventoryByStore(storeId);

        ResponseStructure<List<Inventory>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Inventory fetched successfully");
        rs.setData(list);

        return rs;
    }

    //get orders by store id
    @GetMapping("/{storeId}/orders")
    public ResponseStructure<List<OrdersResponseDto>> getOrders(@PathVariable int storeId) {

        // Service returns DTOs
        List<OrdersResponseDto> data = ordersService.getOrdersByStore(storeId);

        ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Orders fetched successfully");
        rs.setData(data);

        return rs;
    }

    // Get Store by Name
    @GetMapping("/name/{storeName}")
    public ResponseStructure<StoresResponseDto> getStoreByName(@PathVariable String storeName) {

        StoresResponseDto response = service.getStoreByName(storeName);

        ResponseStructure<StoresResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Store fetched successfully by name");
        rs.setData(response);

        return rs;
    }
}