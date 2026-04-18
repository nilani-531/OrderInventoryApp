package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrdersServiceInterface;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrdersServiceInterface service;

    // CREATE ORDER
    @PostMapping
    public ResponseStructure<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto dto) {

        OrdersResponseDto response = service.createOrder(dto);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Order created successfully");
        rs.setData(response);

        return rs;
    }

    // GET ALL ORDERS
    @GetMapping
    public ResponseStructure<List<OrdersResponseDto>> getAllOrders() {

        List<OrdersResponseDto> list = service.getAllOrders();

        ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Orders fetched successfully");
        rs.setData(list);

        return rs;
    }

    // GET ORDER BY ID
    @GetMapping("/{orderId}")
    public ResponseStructure<OrdersResponseDto> getOrder(@PathVariable int orderId) {

        OrdersResponseDto response = service.getOrderById(orderId);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order fetched successfully");
        rs.setData(response);

        return rs;
    }

    // DELETE ORDER
    @DeleteMapping("/{orderId}")
    public ResponseStructure<String> deleteOrder(@PathVariable int orderId) {

        service.deleteOrder(orderId);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order deleted successfully");
        rs.setData("Deleted");

        return rs;
    }

    // UPDATE ORDER STATUS
    @PutMapping("/{orderId}/status")
    public ResponseStructure<OrdersResponseDto> updateStatus(
            @PathVariable int orderId,
            @RequestParam OrderStatus status) {

        OrdersResponseDto response = service.updateOrderStatus(orderId, status);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order status updated successfully");
        rs.setData(response);

        return rs;
    }
}