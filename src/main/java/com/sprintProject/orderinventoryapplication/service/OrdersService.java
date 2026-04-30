package com.sprintProject.orderinventoryapplication.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sprintProject.orderinventoryapplication.entity.Customers;
import com.sprintProject.orderinventoryapplication.entity.Orders;
import com.sprintProject.orderinventoryapplication.entity.OrderStatus;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository;
import com.sprintProject.orderinventoryapplication.repository.OrderItemsRepository;
import com.sprintProject.orderinventoryapplication.repository.OrdersRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.orderinventoryapplication.customexception.StoreNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.OrderNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.CustomerIdNotFoundException;
import com.sprintProject.orderinventoryapplication.customexception.InvalidStatusTransitionException;
import com.sprintProject.orderinventoryapplication.customexception.InvalidInputException;
@Service
public class OrdersService implements OrdersServiceInterface {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private StoresRepository storesRepository;

    // Maps an Orders entity to an OrdersResponseDto
    private OrdersResponseDto mapToResponse(Orders order) {
        OrdersResponseDto dto = new OrdersResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderStatusS(order.getOrderStatus());
        dto.setOrderTms(order.getOrderTms());
        if (order.getCustomers() != null) {
            dto.setCustomerId(order.getCustomers().getCustomerId());
        }
        if (order.getStores() != null) {
            dto.setStoreId(order.getStores().getStoreId());
        }
        return dto;
    }

    // Create the Order
    @Override
    public OrdersResponseDto createOrder(OrdersRequestDto dto) {
        Customers customer = customersRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new CustomerIdNotFoundException("Customer not found"));

        Stores store = storesRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        Orders order = new Orders();
        order.setCustomers(customer);
        order.setStores(store);
        order.setOrderStatus(OrderStatus.OPEN);
        order.setOrderTms(LocalDateTime.now());

        return mapToResponse(ordersRepository.save(order));
    }

    // View All Orders
    @Override
    public List<OrdersResponseDto> getAllOrders() {
        return ordersRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get By Order id
    @Override
    public OrdersResponseDto getOrderById(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return mapToResponse(order);
    }

    // Delete the Order (deletes related order items first to avoid FK constraint)
    @Override
    @Transactional
    public void deleteOrder(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        // Bulk-delete all associated order items in a single SQL statement
        // (avoids Hibernate "Duplicate identifier" session conflict)
        orderItemsRepository.deleteAllByOrderId(orderId);

        // Now safe to delete the order
        ordersRepository.delete(order);
    }

    // Update Order status with lifecycle validation
    @Override
    public OrdersResponseDto updateOrderStatus(int orderId, OrderStatus newStatus) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        OrderStatus current = order.getOrderStatus();

        boolean valid = (current == OrderStatus.OPEN    && newStatus == OrderStatus.PAID)      ||
                        (current == OrderStatus.OPEN    && newStatus == OrderStatus.CANCELLED)  ||
                        (current == OrderStatus.PAID    && newStatus == OrderStatus.SHIPPED)    ||
                        (current == OrderStatus.PAID    && newStatus == OrderStatus.REFUNDED)   ||
                        (current == OrderStatus.SHIPPED && newStatus == OrderStatus.COMPLETE);

        if (!valid) {
            throw new InvalidStatusTransitionException(
                    "Invalid status transition: " + current + " → " + newStatus);
        }

        order.setOrderStatus(newStatus);
        return mapToResponse(ordersRepository.save(order));
    }

    // Update Order store
    @Override
    public OrdersResponseDto updateOrderStore(int orderId, int storeId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Stores store = storesRepository.findById(storeId)
                .orElseThrow(() -> new StoreNotFoundException("Store not found"));

        order.setStores(store);
        return mapToResponse(ordersRepository.save(order));
    }

    // Update Order customer
    @Override
    public OrdersResponseDto updateOrderCustomer(int orderId, int customerId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Customers customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException("Customer not found"));

        order.setCustomers(customer);
        return mapToResponse(ordersRepository.save(order));
    }

    // Update Order timestamp
    @Override
    public OrdersResponseDto updateOrderTms(int orderId, LocalDateTime orderTms) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setOrderTms(orderTms);
        return mapToResponse(ordersRepository.save(order));
    }


    // Get orders by customer ID
    @Override
    public List<OrdersResponseDto> getOrdersByCustomerId(int customerId) {
        return ordersRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get orders by store ID
    @Override
    public List<OrdersResponseDto> getOrdersByStoreId(int storeId) {
        if (!storesRepository.existsById(storeId)) {
            throw new StoreNotFoundException("Store not found");
        }
        return ordersRepository.findByStoreId(storeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get orders by status
    @Override
    public List<OrdersResponseDto> getOrdersByStatus(OrderStatus status) {
        return ordersRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get orders between date range
    @Override
    public List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {
        return ordersRepository.findByDateRange(start, end)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Count orders by status
    @Override
    public long getOrdersCountByStatus(OrderStatus status) {
        if (status == null) {
            throw new InvalidInputException("Order status cannot be null");
        }
        return ordersRepository.countOrdersByStatus(status);
    }
}

