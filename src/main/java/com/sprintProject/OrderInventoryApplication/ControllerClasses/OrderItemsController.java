package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrderItemsService;

@RestController
@RequestMapping("/api/orders")
public class OrderItemsController {

    @Autowired
    private OrderItemsService service;

   

    // ADD ITEM
    @PostMapping("/{orderId}/items")
    public OrderItems addItem(@PathVariable int orderId,
                             @RequestBody OrderItems item) {
        return service.addItem(orderId, item);
    }
}
