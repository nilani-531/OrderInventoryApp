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

    // 1. GET ITEMS BY ORDER ID - SUCCESS
    @Test
    void testGetItemsByOrderIdSuccess() {
        when(itemRepo.findAll()).thenReturn(List.of(item));

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        assertEquals(1, result.size());
    }

    // 2. GET ITEMS BY ORDER ID - EMPTY
    @Test
    void testGetItemsByOrderIdEmpty() {
        when(itemRepo.findAll()).thenReturn(List.of());

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        assertEquals(0, result.size());
    }

    // 3. ADD ITEM - SUCCESS
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

    // 4. ADD ITEM - ORDER NOT FOUND
    @Test
    void testAddItemOrderNotFound() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();

        when(orderRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.addItem(1, 1, dto));
    }

    // 5. ADD ITEM - PRODUCT NOT FOUND
    @Test
    void testAddItemProductNotFound() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();

        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.addItem(1, 1, dto));
    }

    // 6. UPDATE ITEM - SUCCESS
    @Test
    void testUpdateItemSuccess() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(5);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));
        when(itemRepo.save(any())).thenReturn(item);

        assertEquals(5, item.getQuantity());
    }

    // 7. UPDATE ITEM - ITEM NOT FOUND
    @Test
    void testUpdateItemNotFound() {
        when(itemRepo.findById(10)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.updateItem(1, 10, new OrderItemsRequestDto()));
    }

    // 8. UPDATE ITEM - WRONG ORDER
    @Test
    void testUpdateItemWrongOrder() {
        item.getOrders().setOrderId(2);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        assertThrows(RuntimeException.class,
                () -> service.updateItem(1, 10, new OrderItemsRequestDto()));
    }

    // 9. DELETE ITEM - SUCCESS
    @Test
    void testDeleteItemSuccess() {
        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        service.deleteItem(1, 10);

        verify(itemRepo, times(1)).delete(item);
    }

    // 10. DELETE ITEM - ITEM NOT FOUND
    @Test
    void testDeleteItemNotFound() {
        when(itemRepo.findById(10)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.deleteItem(1, 10));
    }

    // 11. DELETE ITEM - WRONG ORDER
    @Test
    void testDeleteItemWrongOrder() {
        item.getOrders().setOrderId(2);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));

        assertThrows(RuntimeException.class,
                () -> service.deleteItem(1, 10));
    }

    // 12. MAPPING CHECK
    @Test
    void testMappingFields() {
        when(itemRepo.findAll()).thenReturn(List.of(item));

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        OrderItemsResponseDto dto = result.get(0);

        assertEquals(item.getLineItemId(), dto.getLineItemId());
        assertEquals(item.getQuantity(), dto.getQuantity());
    }

    // 13. ADD ITEM - ZERO QUANTITY (EDGE CASE)
    @Test
    void testAddItemZeroQuantity() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(0);

        when(orderRepo.findById(1)).thenReturn(Optional.of(order));
        when(productRepo.findById(1)).thenReturn(Optional.of(product));
        when(itemRepo.save(any())).thenReturn(item);

        OrderItemsResponseDto result = service.addItem(1, 1, dto);

        assertNotNull(result);
    }

    // 14. UPDATE ITEM - SAME QUANTITY
    @Test
    void testUpdateItemSameQuantity() {
        OrderItemsRequestDto dto = new OrderItemsRequestDto();
        dto.setQuantity(2);

        when(itemRepo.findById(10)).thenReturn(Optional.of(item));
        when(itemRepo.save(any())).thenReturn(item);

        OrderItemsResponseDto result = service.updateItem(1, 10, dto);

        assertEquals(2, result.getQuantity());
    }

    // 15. GET ITEMS FILTER LOGIC
    @Test
    void testFilterByOrderId() {
        Orders anotherOrder = new Orders();
        anotherOrder.setOrderId(2);

        OrderItems item2 = new OrderItems();
        item2.setOrders(anotherOrder);

        when(itemRepo.findAll()).thenReturn(List.of(item, item2));

        List<OrderItemsResponseDto> result = service.getItemsByOrderId(1);

        assertEquals(1, result.size());
    }
}

