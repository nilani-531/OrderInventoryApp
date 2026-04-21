package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private OrdersServiceInterface ordersServiceInterface;

    // Create Order
    @PostMapping
    public ResponseStructure<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto dto) {

        OrdersResponseDto response = ordersServiceInterface.createOrder(dto);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Order created successfully");
        rs.setData(response);

        return rs;
    }

    @GetMapping("/count/{status}")
    public ResponseEntity<Long> getCount(@PathVariable OrderStatus status) {
        long count = ordersServiceInterface.getOrdersCountByStatus(status);
        return ResponseEntity.ok(count);
    }
    
    // Get all Orders
    @GetMapping
    public ResponseStructure<List<OrdersResponseDto>> getAllOrders() {

        List<OrdersResponseDto> list = ordersServiceInterface.getAllOrders();

        ResponseStructure<List<OrdersResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Orders fetched successfully");
        rs.setData(list);

        return rs;
    }

    // Get Order by id 
    @GetMapping("/{orderId}")
    public ResponseStructure<OrdersResponseDto> getOrder(@PathVariable int orderId) {

        OrdersResponseDto response = ordersServiceInterface.getOrderById(orderId);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order fetched successfully");
        rs.setData(response);

        return rs;
    }

    // Delete Order
    @DeleteMapping("/{orderId}")
    public ResponseStructure<String> deleteOrder(@PathVariable int orderId) {

    	ordersServiceInterface.deleteOrder(orderId);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order deleted successfully");
        rs.setData("Deleted");

        return rs;
    }

    // Update Order status
    @PutMapping("/{orderId}/status")
    public ResponseStructure<OrdersResponseDto> updateStatus(
            @PathVariable int orderId,
            @RequestParam OrderStatus status) {

        OrdersResponseDto response = ordersServiceInterface.updateOrderStatus(orderId, status);

        ResponseStructure<OrdersResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Order status updated successfully");
        rs.setData(response);

        return rs;
    }
    
 // Get orders count by status
    @GetMapping("/count")
    public ResponseStructure<Long> getOrdersCountByStatus(
            @RequestParam OrderStatus status) {

        long count = ordersServiceInterface.getOrdersCountByStatus(status);

        ResponseStructure<Long> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Orders count fetched successfully");
        responseStructure.setData(count);

        return responseStructure;
    }
}