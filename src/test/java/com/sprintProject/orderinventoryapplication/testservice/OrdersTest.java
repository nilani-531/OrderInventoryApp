package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.sprintProject.orderinventoryapplication.entity.*;
import com.sprintProject.orderinventoryapplication.repository.*;
import com.sprintProject.orderinventoryapplication.service.OrdersService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.orderinventoryapplication.customexception.StoreNotFoundException;

class OrdersTest {

    @InjectMocks
    private OrdersService service;

    @Mock
    private OrdersRepository repo;

    @Mock
    private CustomersRepository customerRepo;

    @Mock
    private StoresRepository storeRepo;

    @Mock
    private OrderItemsRepository orderItemsRepo;

    private Customers customer;
    private Stores store;
    private Orders order;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        customer = new Customers();
        customer.setCustomerId(1);

        store = new Stores();
        store.setStoreId(1);

        order = new Orders();
        order.setOrderId(1);
        order.setCustomers(customer);
        order.setStores(store);
        order.setOrderStatus(OrderStatus.OPEN);
        order.setOrderTms(LocalDateTime.now());
    }

    // ------------------ POSITIVE ------------------

    // 1 CREATE ORDER SUCCESS
    @Test
    void testCreateOrderSuccess() {
        OrdersRequestDto dto = new OrdersRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(storeRepo.findById(1)).thenReturn(Optional.of(store));
        when(repo.save(any())).thenReturn(order);

        OrdersResponseDto result = service.createOrder(dto);

        assertNotNull(result);
        assertEquals(OrderStatus.OPEN, result.getOrderStatusS());
    }

    // 2  GET ALL ORDERS
    @Test
    void testGetAllOrders() {
        when(repo.findAll()).thenReturn(List.of(order));

        assertEquals(1, service.getAllOrders().size());
    }

    // 3  GET ORDER BY ID
    @Test
    void testGetOrderByIdSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));

        assertEquals(1, service.getOrderById(1).getOrderId());
    }

    // 4  DELETE ORDER SUCCESS
    @Test
    void testDeleteOrderSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));

        service.deleteOrder(1);

        verify(orderItemsRepo).deleteAllByOrderId(1);
        verify(repo).delete(order);
    }

    // 5  VALID STATUS TRANSITION
    @Test
    void testUpdateOrderStatusValid() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(repo.save(any())).thenReturn(order);

        OrdersResponseDto result = service.updateOrderStatus(1, OrderStatus.PAID);

        assertEquals(OrderStatus.PAID, result.getOrderStatusS());
    }

    // 6  UPDATE STORE SUCCESS
    @Test
    void testUpdateOrderStoreSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(storeRepo.findById(1)).thenReturn(Optional.of(store));
        when(repo.save(any())).thenReturn(order);

        assertNotNull(service.updateOrderStore(1, 1));
    }

    // 7  GET ORDERS BY CUSTOMER
    @Test
    void testGetOrdersByCustomerId() {
        when(repo.findByCustomerId(1)).thenReturn(List.of(order));

        assertEquals(1, service.getOrdersByCustomerId(1).size());
    }

    // ------------------ NEGATIVE ------------------

    // 8  CREATE ORDER - CUSTOMER NOT FOUND
    @Test
    void testCreateOrderCustomerNotFound() {
        OrdersRequestDto dto = new OrdersRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createOrder(dto));
    }

    // 9  CREATE ORDER - STORE NOT FOUND
    @Test
    void testCreateOrderStoreNotFound() {
        OrdersRequestDto dto = new OrdersRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(storeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createOrder(dto));
    }

    // 10  GET ORDER NOT FOUND
    @Test
    void testGetOrderByIdNotFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getOrderById(1));
    }

    // 11  DELETE ORDER NOT FOUND
    @Test
    void testDeleteOrderNotFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.deleteOrder(1));
    }

    // 12  INVALID STATUS TRANSITION
    @Test
    void testInvalidStatusTransition() {
        order.setOrderStatus(OrderStatus.SHIPPED);

        when(repo.findById(1)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class,
                () -> service.updateOrderStatus(1, OrderStatus.OPEN));
    }

    // 13  UPDATE STORE NOT FOUND
    @Test
    void testUpdateOrderStoreNotFound() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(storeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateOrderStore(1, 1));
    }

    // 14  GET ORDERS BY STORE NOT FOUND
    @Test
    void testGetOrdersByStoreNotFound() {
        when(storeRepo.existsById(1)).thenReturn(false);

        assertThrows(StoreNotFoundException.class,
                () -> service.getOrdersByStoreId(1));
    }

    // 15  COUNT STATUS NULL
    @Test
    void testCountByStatusNull() {
        assertThrows(RuntimeException.class,
                () -> service.getOrdersCountByStatus(null));
    }
}