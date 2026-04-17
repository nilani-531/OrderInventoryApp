package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;

@Service
public class OrdersService implements OrdersServiceInterface {

    @Autowired
    private OrdersRepository repo;

    
    private Orders mapToEntity(OrdersRequestDto dto) {
        Orders order = new Orders();
        order.setCustomers(dto.getCustomers());
        order.setStores(dto.getStores());
        order.setOrderStatus(dto.getOrderStatusS());
        order.setOrderTms(dto.getOrderTms());
        return order;
    }

    // ENTITY → DTO
    private OrdersResponseDto mapToResponse(Orders order) {
        OrdersResponseDto dto = new OrdersResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setCustomers(order.getCustomers());
        dto.setStores(order.getStores());
        dto.setOrderStatusS(order.getOrderStatus());
        dto.setOrderTms(order.getOrderTms());
        return dto;
    }

    //  CREATE
    @Override
    public OrdersResponseDto createOrder(OrdersRequestDto orderDto) {

        Orders order = mapToEntity(orderDto);

        // default values
        order.setOrderStatus(OrderStatus.OPEN);
        order.setOrderTms(LocalDateTime.now());

        Orders saved = repo.save(order);

        return mapToResponse(saved);
    }

    // GET ALL
    @Override
    public List<OrdersResponseDto> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @Override
    public OrdersResponseDto getOrderById(int orderId) {

        Orders order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }

    // ✅ DELETE
    @Override
    public void deleteOrder(int orderId) {
        repo.deleteById(orderId);
    }

    //  UPDATE STATUS (your business logic preserved 💯)
    @Override
    public OrdersResponseDto updateOrderStatus(int orderId, OrderStatus newStatus) {

        Orders order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus current = order.getOrderStatus();

        if ((current == OrderStatus.OPEN && newStatus == OrderStatus.PAID) ||
            (current == OrderStatus.OPEN && newStatus == OrderStatus.CANCELLED) ||
            (current == OrderStatus.PAID && newStatus == OrderStatus.SHIPPED) ||
            (current == OrderStatus.PAID && newStatus == OrderStatus.REFUNDED) ||
            (current == OrderStatus.SHIPPED && newStatus == OrderStatus.COMPLETE)) {

            order.setOrderStatus(newStatus);
        } else {
            throw new RuntimeException("Invalid status transition");
        }

        Orders updated = repo.save(order);

        return mapToResponse(updated);
    }
}