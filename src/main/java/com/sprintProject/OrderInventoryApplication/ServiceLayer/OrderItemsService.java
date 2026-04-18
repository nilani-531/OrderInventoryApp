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
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrdersRepository ordersRepository;


    @Autowired
    private ProductsRepository productRepo;

    // 🔹 GET ITEMS BY ORDER ID
    @Override
    public List<OrderItemsResponseDto> getItemsByOrderId(int orderId) {

        return itemRepo.findAll()
                .stream()
                .filter(item -> item.getOrders().getOrderId() == orderId)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔹 ADD ITEM
    @Override
    public OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto) {

    // 🔹 GET
    @Override
    public List<OrderItemsResponseDto> getItemsByOrderId(int orderId) {

        // check order exists
        ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // filter manually
        return orderItemsRepository.findAll()
                .stream()
                .filter(item -> item.getOrders().getOrderId() == orderId)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    // 🔹 ADD
    @Override
    public OrderItemsResponseDto addItem(int orderId, OrderItemsRequestDto dto) {


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

        if (dto.getQuantity() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderItems item = new OrderItems();
        item.setOrders(order);
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        // ⚠️ product NOT set (because DTO doesn't have productId)

        return mapToDto(orderItemsRepository.save(item));
    }

    // 🔹 UPDATE

    @Override
    public OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto) {

        OrderItems item = orderItemsRepository.findById(lineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (item.getOrders().getOrderId() != orderId) {

            throw new RuntimeException("Item does not belong to this order");
        }

        item.setQuantity(dto.getQuantity());

        return mapToResponse(itemRepo.save(item));
    }

    // 🔹 DELETE ITEM

            throw new InvalidDataException("Item does not belong to this order");
        }

        if (dto.getQuantity() <= 0) {
            throw new InvalidDataException("Quantity must be greater than 0");
        }

        // unit price immutable if shipment exists
        if (item.getShipments() != null &&
                item.getUnitPrice() != dto.getUnitPrice()) {

            throw new InvalidDataException("Unit price cannot be changed after shipment");
        }

        item.setQuantity(dto.getQuantity());

        return mapToDto(orderItemsRepository.save(item));
    }

    // 🔹 DELETE

    @Override
    public void deleteItem(int orderId, int lineItemId) {

        OrderItems item = orderItemsRepository.findById(lineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (item.getOrders().getOrderId() != orderId) {

            throw new RuntimeException("Item does not belong to this order");

            throw new InvalidDataException("Item does not belong to this order");

        }

        orderItemsRepository.delete(item);
    }


    // 🔹 MAPPER
    private OrderItemsResponseDto mapToResponse(OrderItems item) {

        OrderItemsResponseDto dto = new OrderItemsResponseDto();

        dto.setLineItemId(item.getLineItemId());
        dto.setOrderId(item.getOrders().getOrderId());
        dto.setProductId(item.getProducts().getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());


    // 🔹 DTO MAPPER
    private OrderItemsResponseDto mapToDto(OrderItems item) {
        OrderItemsResponseDto dto = new OrderItemsResponseDto();
        dto.setLineItemId(item.getLineItemId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());

        return dto;
    }
}