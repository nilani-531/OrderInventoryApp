package com.sprintProject.orderinventoryapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.orderinventoryapplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.service.OrderItemsServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders/{orderId}/items")
public class OrderItemsController {

    @Autowired
    private OrderItemsServiceInterface orderItemsServiceInterface;

    // GET /api/orders/{orderId}/items
    @GetMapping
    public ResponseStructure<List<OrderItemsResponseDto>> getItemsByOrder(@PathVariable Integer orderId) {
        List<OrderItemsResponseDto> data = orderItemsServiceInterface.getItemsByOrderId(orderId);
        ResponseStructure<List<OrderItemsResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Items fetched successfully");
        rs.setData(data);
        return rs;
    }

    // POST /api/orders/{orderId}/items?productId=
    @PostMapping
    public ResponseStructure<OrderItemsResponseDto> addItem(
            @PathVariable Integer orderId,
            @RequestParam Integer productId,@Valid
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = orderItemsServiceInterface.addItem(orderId, productId, dto);
        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Item added successfully");
        rs.setData(data);
        return rs;
    }

   
    @PutMapping("/{lineItemId}")
    public ResponseStructure<OrderItemsResponseDto> updateItem(
            @PathVariable Integer orderId,
            @PathVariable Integer lineItemId,
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = orderItemsServiceInterface.updateItem(orderId, lineItemId, dto);
        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item updated successfully");
        rs.setData(data);
        return rs;
    }

   
    @DeleteMapping("/{lineItemId}")
    public ResponseStructure<String> deleteItem(
            @PathVariable Integer orderId,
            @PathVariable Integer lineItemId) {

        orderItemsServiceInterface.deleteItem(orderId, lineItemId);
        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item deleted successfully");
        rs.setData("Deleted item with id: " + lineItemId);
        return rs;
    }
}

