package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.sprintProject.orderinventoryapplication.entity.*;
import com.sprintProject.orderinventoryapplication.repository.*;
import com.sprintProject.orderinventoryapplication.service.OrderItemsService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.orderinventoryapplication.customexception.InvalidDataException;

class OrderItemsTest {

    @InjectMocks
    private OrderItemsService service;

    @Mock
    private OrderItemsRepository itemRepo;

    @Mock
    private OrdersRepository orderRepo;

    @Mock
    private ProductsRepository productRepo;

    private Orders order;
    private Products product;
    private OrderItems item;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        order = new Orders();
        order.setOrderId(1);

        product = new Products();
        product.setProductId(1);

        item = new OrderItems();
        item.setLineItemId(10);
        item.setOrders(order);
        item.setProducts(product);
        item.setQuantity(2);
        item.setUnitPrice(100);
    }

    // ------------------ POSITIVE TEST CASES ------------------

    // 1  GET ITEMS BY ORDER ID - SUCCESS
    @Test
    void testGetItemsByOrderIdSuccess() {
        when(itemRepo.findByOrderId(1)).thenReturn(List.of(item));

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        assertEquals(1, result.size());
    }

    // 2 GET ITEMS BY ORDER ID - EMPTY
    @Test
    void testGetItemsByOrderIdEmpty() {
        when(itemRepo.findByOrderId(1)).thenReturn(List.of());

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        assertTrue(result.isEmpty());
    }

    // 3  ADD ITEM - SUCCESS
    @Test
    void testAddItemSuccess() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(2);
        dto.setUnitPrice(200);

        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(itemRepo.save(any())).thenReturn(item);

        OrderItemsResponseDto result = service.addItem(1, 1, dto);

        assertNotNull(result);
        assertEquals(1, result.getOrderId());
    }

    // 4  UPDATE ITEM - SUCCESS
    @Test
    void testUpdateItemSuccess() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(5);
        dto.setUnitPrice(100);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));
        when(itemRepo.save(any())).thenReturn(item);

        OrderItemsResponseDto result = service.updateItem(1, 10, dto);

        assertEquals(5, result.getQuantity());
    }

    // 5  DELETE ITEM - SUCCESS
    @Test
    void testDeleteItemSuccess() {
        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        service.deleteItem(1, 10);

        verify(itemRepo, times(1)).delete(item);
    }

    // 6  GET ITEMS BY PRODUCT ID - SUCCESS
    @Test
    void testGetItemsByProductIdSuccess() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(itemRepo.findItemsByProductId(1)).thenReturn(List.of(item));

        List<OrderItemsResponseDto> result = service.getItemsByProductId(1);

        assertEquals(1, result.size());
    }

    // 7  GET TOTAL QUANTITY - SUCCESS
    @Test
    void testGetTotalQuantitySuccess() {
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(itemRepo.getTotalQuantityByProductId(1)).thenReturn(10);

        Integer total = service.getTotalQuantityByProductId(1);

        assertEquals(10, total);
    }

    // ------------------ NEGATIVE TEST CASES ------------------

    // 8  ADD ITEM - ORDER NOT FOUND
    @Test
    void testAddItemOrderNotFound() {
        when(orderRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.addItem(1, 1, new OrderItemsRequestDto()));
    }

    // 9  ADD ITEM - PRODUCT NOT FOUND
    @Test
    void testAddItemProductNotFound() {
        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.addItem(1, 1, new OrderItemsRequestDto()));
    }

    // 10  ADD ITEM - INVALID QUANTITY
    @Test
    void testAddItemInvalidQuantity() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(0);

        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.of(product));

        assertThrows(InvalidDataException.class,
                () -> service.addItem(1, 1, dto));
    }

    // 11  UPDATE ITEM - NOT FOUND
    @Test
    void testUpdateItemNotFound() {
        when(itemRepo.findById(10)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateItem(1, 10, new OrderItemsRequestDto()));
    }

    // 12  UPDATE ITEM - WRONG ORDER
    @Test
    void testUpdateItemWrongOrder() {
        item.getOrders().setOrderId(2);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        assertThrows(InvalidDataException.class,
                () -> service.updateItem(1, 10, new OrderItemsRequestDto()));
    }

    // 13  UPDATE ITEM - INVALID QUANTITY
    @Test
    void testUpdateItemInvalidQuantity() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(0);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        assertThrows(InvalidDataException.class,
                () -> service.updateItem(1, 10, dto));
    }

    // 14  DELETE ITEM - NOT FOUND
    @Test
    void testDeleteItemNotFound() {
        when(itemRepo.findById(10)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.deleteItem(1, 10));
    }

    // 15  DELETE ITEM - WRONG ORDER
    @Test
    void testDeleteItemWrongOrder() {
        item.getOrders().setOrderId(2);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        assertThrows(InvalidDataException.class,
                () -> service.deleteItem(1, 10));
    }
}