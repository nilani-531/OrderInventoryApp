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
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private StoresRepository storesRepository;

    private OrdersResponseDto mapToResponse(Orders order) {
        OrdersResponseDto dto = new OrdersResponseDto();
        dto.setOrderId(order.getOrderId());
        dto.setOrderStatusS(order.getOrderStatus());
        dto.setOrderTms(order.getOrderTms());
        return dto;
    }

    // Create the Order
    @Override
    public OrdersResponseDto createOrder(OrdersRequestDto dto) {
        Customers customer = customersRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Stores store = storesRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

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
                .collect(Collectors.toList());
    }

    // Get By Order id
    @Override
    public OrdersResponseDto getOrderById(int orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToResponse(order);
    }

    // Delete the Order
    @Override
    public void deleteOrder(int orderId) {
        ordersRepository.deleteById(orderId);
    }

    // Update Order status with lifecycle validation
    @Override
    public OrdersResponseDto updateOrderStatus(int orderId, OrderStatus newStatus) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus current = order.getOrderStatus();

        boolean valid = (current == OrderStatus.OPEN    && newStatus == OrderStatus.PAID)      ||
                        (current == OrderStatus.OPEN    && newStatus == OrderStatus.CANCELLED)  ||
                        (current == OrderStatus.PAID    && newStatus == OrderStatus.SHIPPED)    ||
                        (current == OrderStatus.PAID    && newStatus == OrderStatus.REFUNDED)   ||
                        (current == OrderStatus.SHIPPED && newStatus == OrderStatus.COMPLETE);

        if (!valid) {
            throw new RuntimeException(
                    "Invalid status transition: " + current + " → " + newStatus);
        }

        order.setOrderStatus(newStatus);
        return mapToResponse(ordersRepository.save(order));
    }

    // Update Order store
    @Override
    public OrdersResponseDto updateOrderStore(int orderId, int storeId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Stores store = storesRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        order.setStores(store);
        return mapToResponse(ordersRepository.save(order));
    }

    // Update Order customer
    @Override
    public OrdersResponseDto updateOrderCustomer(int orderId, int customerId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Customers customer = customersRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomers(customer);
        return mapToResponse(ordersRepository.save(order));
    }

    // GET /api/orders/customer/{customerId}
    // BUG FIX: was using findAll() + in-memory filter — replaced with JPA query
    @Override
    public List<OrdersResponseDto> getOrdersByCustomerId(int customerId) {
        return ordersRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET /api/orders/store/{storeId}
    // BUG FIX: was using findAll() + in-memory filter — replaced with JPA query
    // Also: StoresController called getOrdersByStore() which didn't match method name —
    // method is now exposed via getOrdersByStoreId() and aliased below.
    @Override
    public List<OrdersResponseDto> getOrdersByStoreId(int storeId) {
        return ordersRepository.findByStoreId(storeId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET /api/orders/status/{status}
    // BUG FIX: was using findAll() + in-memory filter — replaced with JPA query
    @Override
    public List<OrdersResponseDto> getOrdersByStatus(OrderStatus status) {
        return ordersRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // GET /api/orders/date-range?from=&to=
    // BUG FIX: was using findAll() + in-memory filter — replaced with JPA query
    @Override
    public List<OrdersResponseDto> getOrdersBetweenDates(LocalDateTime start, LocalDateTime end) {
        return ordersRepository.findByDateRange(start, end)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Count orders by status
    @Override
    public long getOrdersCountByStatus(OrderStatus status) {
        if (status == null) {
            throw new RuntimeException("Order status cannot be null");
        }
        return ordersRepository.countOrdersByStatus(status);
    }
}