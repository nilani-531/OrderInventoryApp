package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrderItemsRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.OrdersRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.OrderItemsRequestDto;

@Service
public class OrderItemsService implements OrderItemsServiceInterface {

    @Autowired
    private OrderItemsRepository itemRepo;

    @Autowired
    private OrdersRepository orderRepo;

    // ADD ITEM
    @Override
    public OrderItems addItem(int orderId, OrderItemsRequestDto dto) {

        Orders order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderItems item = new OrderItems();

        // Relationships
        item.setOrders(order); // from path variable
        item.setProducts(dto.getProducts());
        item.setShipments(dto.getShipments());

        // Fields
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        return itemRepo.save(item);
    }

    // UPDATE ITEM
    @Override
    public OrderItems updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto) {

        OrderItems item = itemRepo.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Optional check (good practice)
        if (item.getOrders().getOrderId() != orderId) {
            throw new RuntimeException("Item does not belong to given order");
        }

        //  Update relationships
        item.setProducts(dto.getProducts());
        item.setShipments(dto.getShipments());

        //  Update fields
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        return itemRepo.save(item);
    }

    //  DELETE ITEM
    @Override
    public void deleteItem(int orderId, int lineItemId) {

        OrderItems item = itemRepo.findById(lineItemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Safety check
        if (item.getOrders().getOrderId() != orderId) {
            throw new RuntimeException("Item does not belong to given order");
        }

        itemRepo.delete(item);
    }

  
}