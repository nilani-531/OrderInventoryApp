package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrderItemsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ProductsRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.*;

@Service
public class OrderItemsService implements OrderItemsServiceInterface {

    @Autowired
    private OrderItemsRepository itemRepo;

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private ProductsRepository productRepo;

    // 🔹 GET ITEMS BY ORDER ID
    @Override
    public List<OrderItemsResponseDto> getItemsByOrderId(int orderId) {
        return itemRepo.findAll()
                .stream()
                .filter(item -> item.getOrders() != null && item.getOrders().getOrderId() == orderId)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 ADD ITEM
    @Override
    public OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto) {
        Orders order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Products product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItems item = new OrderItems();
        item.setOrders(order);
        item.setProducts(product);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        return mapToResponse(itemRepo.save(item));
    }

    // 🔹 UPDATE ITEM
    @Override
    public OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto) {
        OrderItems item = itemRepo.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getOrders() == null || item.getOrders().getOrderId() != orderId) {
            throw new InvalidDataException("Item does not belong to this order");
        }

        if (dto.getQuantity() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        // unit price immutable if shipment exists
        if (item.getShipments() != null && item.getUnitPrice() != dto.getUnitPrice()) {
            throw new InvalidDataException("Unit price cannot be changed after shipment");
        }

        item.setQuantity(dto.getQuantity());
        // only change unit price if allowed
        if (item.getShipments() == null) {
            item.setUnitPrice(dto.getUnitPrice());
        }

        return mapToResponse(itemRepo.save(item));
    }

    // 🔹 DELETE ITEM
    @Override
    public void deleteItem(int orderId, int lineItemId) {
        OrderItems item = itemRepo.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (item.getOrders() == null || item.getOrders().getOrderId() != orderId) {
            throw new InvalidDataException("Item does not belong to this order");
        }

        itemRepo.delete(item);
    }

    // 🔹 MAPPER
    private OrderItemsResponseDto mapToResponse(OrderItems item) {
        OrderItemsResponseDto dto = new OrderItemsResponseDto();
        dto.setLineItemId(item.getLineItemId());
        dto.setOrderId(item.getOrders() != null ? item.getOrders().getOrderId() : 0);
        dto.setProductId(item.getProducts() != null ? item.getProducts().getProductId() : 0);
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        return dto;
    }

	}