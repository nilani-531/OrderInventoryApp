package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrderItemsServiceInterface;

@RestController
@RequestMapping("/api/orders")
public class OrderItemsController {

    @Autowired
    private OrderItemsServiceInterface service;

    //  GET ITEMS BY ORDER ID
    @GetMapping("/{orderId}/items")
    public ResponseStructure<List<OrderItemsResponseDto>> getItems(@PathVariable int orderId) {

        List<OrderItemsResponseDto> data = service.getItemsByOrderId(orderId);

        ResponseStructure<List<OrderItemsResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Items fetched successfully");
        rs.setData(data);

        return rs;
    }

    //  ADD ITEM (IMPORTANT FIX: productId added)
    @PostMapping("/{orderId}/items/{productId}")
    public ResponseStructure<OrderItemsResponseDto> addItem(
            @PathVariable int orderId,
            @PathVariable int productId,
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = service.addItem(orderId, productId, dto);

        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Item added successfully");
        rs.setData(data);

        return rs;
    }

    //  UPDATE ITEM
    @PutMapping("/{orderId}/items/{lineItemId}")
    public ResponseStructure<OrderItemsResponseDto> updateItem(
            @PathVariable int orderId,
            @PathVariable int lineItemId,
            @RequestBody OrderItemsRequestDto dto) {

        OrderItemsResponseDto data = service.updateItem(orderId, lineItemId, dto);

        ResponseStructure<OrderItemsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item updated successfully");
        rs.setData(data);

        return rs;
    }

    //  DELETE ITEM
    @DeleteMapping("/{orderId}/items/{lineItemId}")
    public ResponseStructure<String> deleteItem(
            @PathVariable int orderId,
            @PathVariable int lineItemId) {

        service.deleteItem(orderId, lineItemId);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Item deleted successfully");
        rs.setData("Deleted item with id: " + lineItemId);

        return rs;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 0f5d0f5 (Committing Day5 Task)
