package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrderItemsServiceInterface;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemsController {

    @Autowired
    private OrderItemsServiceInterface orderItemsServiceInterface;

    // Get item by Order id
    @GetMapping("/order/{orderId}")
    public ResponseStructure<List<OrderItemsResponseDto>> getItemsByOrder(@PathVariable int orderId) {

        List<OrderItemsResponseDto> data = orderItemsServiceInterface.getItemsByOrderId(orderId);

        ResponseStructure<List<OrderItemsResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Items fetched successfully");
        rs.setData(data);

        return rs;
    }

    // Add item by orderId, productId
    @PostMapping("/order/{orderId}/product/{productId}")
    public ResponseStructure<OrderItemsResponseDto> addItem(
            @PathVariable int orderId,
            @PathVariable int productId,
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = orderItemsServiceInterface.addItem(orderId, productId, dto);

        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Item added successfully");
        rs.setData(data);

        return rs;
    }

    // Update Item
    @PutMapping("/order/{orderId}/item/{lineItemId}")
    public ResponseStructure<OrderItemsResponseDto> updateItem(
            @PathVariable int orderId,
            @PathVariable int lineItemId,
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = orderItemsServiceInterface.updateItem(orderId, lineItemId, dto);

        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item updated successfully");
        rs.setData(data);

        return rs;
    }

    // Delete item
    @DeleteMapping("/order/{orderId}/item/{lineItemId}")
    public ResponseStructure<String> deleteItem(
            @PathVariable int orderId,
            @PathVariable int lineItemId) {

    	orderItemsServiceInterface.deleteItem(orderId, lineItemId);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item deleted successfully");
        rs.setData("Deleted item with id: " + lineItemId);

        return rs;
    }

    // Get items by product id
    @GetMapping("/product/{productId}")
    public ResponseStructure<List<OrderItemsResponseDto>> getItemsByProduct(@PathVariable int productId) {

        List<OrderItemsResponseDto> data = orderItemsServiceInterface.getItemsByProductId(productId);

        ResponseStructure<List<OrderItemsResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Items fetched by product");
        rs.setData(data);

        return rs;
    }

    // Get total quantity by product
    @GetMapping("/product/{productId}/quantity")
    public ResponseStructure<Integer> getTotalQuantity(@PathVariable int productId) {

        Integer data = orderItemsServiceInterface.getTotalQuantityByProductId(productId);

        ResponseStructure<Integer> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Total quantity fetched");
        rs.setData(data);

        return rs;
    }
}