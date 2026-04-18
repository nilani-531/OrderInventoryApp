package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.StoresRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;

@Service
public class OrdersService implements OrdersServiceInterface {

    @Autowired
    private OrdersRepository repo;

    @Autowired
    private CustomersRepository customerRepo;

    @Autowired
    private StoresRepository storeRepo;

    private OrdersResponseDto mapToResponse(Orders order) {
        OrdersResponseDto dto = new OrdersResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderStatusS(order.getOrderStatus());
        dto.setOrderTms(order.getOrderTms());
        return dto;
    }

    @Override
    public OrdersResponseDto createOrder(OrdersRequestDto dto) {

        Customers customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Stores store = storeRepo.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Orders order = new Orders();
        order.setCustomers(customer);
        order.setStores(store);
        order.setOrderStatus(OrderStatus.OPEN);
        order.setOrderTms(LocalDateTime.now());

        Orders saved = repo.save(order);

        return mapToResponse(saved);
    }

    @Override
    public List<OrdersResponseDto> getAllOrders() {
        return repo.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrdersResponseDto getOrderById(int orderId) {

        Orders order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToResponse(order);
    }

    @Override
    public void deleteOrder(int orderId) {
        repo.deleteById(orderId);
    }

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

        return mapToResponse(repo.save(order));
    }

    @Override
    public OrdersResponseDto updateOrderStore(int orderId, int storeId) {

        Orders order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Stores store = storeRepo.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        order.setStores(store);

        return mapToResponse(repo.save(order));
    }

    @Override
    public OrdersResponseDto updateOrderCustomer(int orderId, int customerId) {

        Orders order = repo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customers customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomers(customer);

        return mapToResponse(repo.save(order));
    }

    @Override
    public List<OrdersResponseDto> getOrdersByCustomerId(int customerId) {

        return repo.findAll().stream()
                .filter(o -> o.getCustomers().getCustomerId() == customerId)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponseDto> getOrdersByStoreId(int storeId) {

        return repo.findAll().stream()
                .filter(o -> o.getStores().getStoreId() == storeId)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponseDto> getOrdersByStatus(OrderStatus status) {

        return repo.findAll().stream()
                .filter(o -> o.getOrderStatus() == status)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {

        return repo.findAll().stream()
                .filter(o -> o.getOrderTms() != null &&
                             o.getOrderTms().isAfter(start) &&
                             o.getOrderTms().isBefore(end))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}