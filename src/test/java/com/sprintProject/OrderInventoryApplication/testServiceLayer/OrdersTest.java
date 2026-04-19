package com.sprintProject.OrderInventoryApplication.testServiceLayer;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprintProject.OrderInventoryApplication.EntityClasses.*;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.*;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.OrdersService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrdersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;

@SpringBootTest
public class OrdersTest {

    @InjectMocks
    private OrdersService service;

    @Mock
    private OrdersRepository repo;

    @Mock
    private CustomersRepository customerRepo;

    @Mock
    private StoresRepository storeRepo;

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

    // 1. CREATE ORDER - SUCCESS
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

    // 2. CREATE ORDER - CUSTOMER NOT FOUND
    @Test
    void testCreateOrderCustomerNotFound() {
        OrdersRequestDto dto = new OrdersRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createOrder(dto));
    }

    // 3. CREATE ORDER - STORE NOT FOUND
    @Test
    void testCreateOrderStoreNotFound() {
        OrdersRequestDto dto = new OrdersRequestDto();
        dto.setCustomerId(1);
        dto.setStoreId(1);

        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(storeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createOrder(dto));
    }

    // 4. GET ALL ORDERS
    @Test
    void testGetAllOrders() {
        when(repo.findAll()).thenReturn(List.of(order));

        List<OrdersResponseDto> result = service.getAllOrders();

        assertEquals(1, result.size());
    }

    // 5. GET ORDER BY ID - SUCCESS
    @Test
    void testGetOrderByIdSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));

        OrdersResponseDto result = service.getOrderById(1);

        assertEquals(1, result.getOrderId());
    }

    // 6. GET ORDER BY ID - NOT FOUND
    @Test
    void testGetOrderByIdNotFound() {
        when(repo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getOrderById(1));
    }

    // 7. DELETE ORDER
    @Test
    void testDeleteOrder() {
        doNothing().when(repo).deleteById(1);

        service.deleteOrder(1);

        verify(repo, times(1)).deleteById(1);
    }

    // 8. UPDATE STATUS - VALID TRANSITION
    @Test
    void testUpdateOrderStatusValid() {
        order.setOrderStatus(OrderStatus.OPEN);

        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(repo.save(any())).thenReturn(order);

        OrdersResponseDto result = service.updateOrderStatus(1, OrderStatus.PAID);

        assertEquals(OrderStatus.PAID, result.getOrderStatusS());
    }

    // 9. UPDATE STATUS - INVALID TRANSITION
    @Test
    void testUpdateOrderStatusInvalid() {
        order.setOrderStatus(OrderStatus.SHIPPED);

        when(repo.findById(1)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class,
                () -> service.updateOrderStatus(1, OrderStatus.OPEN));
    }

    // 10. UPDATE STORE - SUCCESS
    @Test
    void testUpdateOrderStoreSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(storeRepo.findById(1)).thenReturn(Optional.of(store));
        when(repo.save(any())).thenReturn(order);

        OrdersResponseDto result = service.updateOrderStore(1, 1);

        assertNotNull(result);
    }

    // 11. UPDATE STORE - STORE NOT FOUND
    @Test
    void testUpdateOrderStoreNotFound() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(storeRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateOrderStore(1, 1));
    }

    // 12. UPDATE CUSTOMER - SUCCESS
    @Test
    void testUpdateOrderCustomerSuccess() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(customerRepo.findById(1)).thenReturn(Optional.of(customer));
        when(repo.save(any())).thenReturn(order);

        OrdersResponseDto result = service.updateOrderCustomer(1, 1);

        assertNotNull(result);
    }

    // 13. UPDATE CUSTOMER - CUSTOMER NOT FOUND
    @Test
    void testUpdateOrderCustomerNotFound() {
        when(repo.findById(1)).thenReturn(Optional.of(order));
        when(customerRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateOrderCustomer(1, 1));
    }

    // 14. GET ORDERS BY CUSTOMER ID
    @Test
    void testGetOrdersByCustomerId() {
        when(repo.findAll()).thenReturn(List.of(order));

        List<OrdersResponseDto> result = service.getOrdersByCustomerId(1);

        assertEquals(1, result.size());
    }

    // 15. GET ORDERS BETWEEN DATES
    @Test
    void testGetOrdersBetweenDates() {
        when(repo.findAll()).thenReturn(List.of(order));

        List<OrdersResponseDto> result = service.getOrdersBetweenDates(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );

        assertEquals(1, result.size());
    }
}
