package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrderItemsServiceInterface;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;

@RestController
@RequestMapping("/api/orders")
public class OrderItemsController {

    @Autowired
    private OrderItemsServiceInterface service;


    //  ADD ITEM
    @PostMapping("/{orderId}/items")
    public ResponseStructure<OrderItems> addItem(@PathVariable int orderId,
                                                @RequestBody OrderItemsRequestDto dto) {

        OrderItems item = service.addItem(orderId, dto);

        ResponseStructure<OrderItems> response = new ResponseStructure<>();
        response.setStatus(201);
        response.setMsg("Item added successfully");
        response.setData(item);

        return response;
    }

    //  UPDATE ITEM
    @PutMapping("/{orderId}/items/{lineItemId}")
    public ResponseStructure<OrderItems> updateItem(@PathVariable int orderId,
                                                   @PathVariable int lineItemId,
                                                   @RequestBody OrderItemsRequestDto dto) {

        OrderItems item = service.updateItem(orderId, lineItemId, dto);

        ResponseStructure<OrderItems> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Item updated successfully");
        response.setData(item);

        return response;
    }

    //  DELETE ITEM
    @DeleteMapping("/{orderId}/items/{lineItemId}")
    public ResponseStructure<String> deleteItem(@PathVariable int orderId,
                                               @PathVariable int lineItemId) {

        service.deleteItem(orderId, lineItemId);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Item deleted successfully");
        response.setData("Deleted");

        return response;
    }
}