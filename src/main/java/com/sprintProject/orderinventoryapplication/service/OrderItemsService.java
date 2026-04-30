package com.sprintProject.orderinventoryapplication.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.orderinventoryapplication.entity.OrderItems;
import com.sprintProject.orderinventoryapplication.entity.Orders;
import com.sprintProject.orderinventoryapplication.entity.Products;
import com.sprintProject.orderinventoryapplication.repository.OrderItemsRepository;
import com.sprintProject.orderinventoryapplication.repository.OrdersRepository;
import com.sprintProject.orderinventoryapplication.repository.ProductsRepository;
import com.sprintProject.orderinventoryapplication.dto.requestDto.OrderItemsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.OrderItemsResponseDto;
import com.sprintProject.orderinventoryapplication.customexception.*;

@Service
public class OrderItemsService implements OrderItemsServiceInterface {

	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Autowired
	private OrdersRepository ordersRepository;

	@Autowired
	private ProductsRepository productsRepository;

	//Get Item By orderId
	@Override
	public List<OrderItemsResponseDto> getItemsByOrderId(int orderId) {

	    //  Check order exists
	    ordersRepository.findById(orderId)
	        .orElseThrow(() -> new InvalidDataException("Order not found"));

	    //  Fetch items
	    List<OrderItems> items = orderItemsRepository.findByOrderId(orderId);

	    //  Optional check (if no items)
	    if (items.isEmpty()) {
	        throw new InvalidDataException("No items found for this order");
	    }

	    return items.stream().map(this::mapToResponse).toList();
	}

	// Add item to order
	@Override
	public OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto) {
		Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new OrderItemNotFoundException("Order not found"));

		
		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new OrderItemNotFoundException("Product not found"));

		if (dto.getQuantity() <= 0) {
			throw new InvalidDataException("Quantity must be greater than 0");
		}

		OrderItems item = new OrderItems();
		item.setOrders(order);
		item.setProducts(product);
		item.setQuantity(dto.getQuantity());
		item.setUnitPrice(dto.getUnitPrice());

		return mapToResponse(orderItemsRepository.save(item));
	}

	// Update item
	@Override
	public OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto) {
		OrderItems item = orderItemsRepository.findById(lineItemId)
				.orElseThrow(() -> new OrderItemNotFoundException("Item not found"));

		if (item.getOrders() == null || item.getOrders().getOrderId() != orderId) {
			throw new InvalidDataException("Item does not belong to this order");
		}

		if (dto.getQuantity() <= 0) {
			throw new InvalidDataException("Quantity must be greater than 0");
		}

		// Unit price immutable after shipment
		if (item.getShipments() != null && item.getUnitPrice() != dto.getUnitPrice()) {
			throw new InvalidDataException("Unit price cannot be changed after shipment");
		}

		item.setQuantity(dto.getQuantity());
		if (item.getShipments() == null) {
			item.setUnitPrice(dto.getUnitPrice());
		}

		return mapToResponse(orderItemsRepository.save(item));
	}

	// Delete item
	@Override
	public void deleteItem(int orderId, int lineItemId) {
		OrderItems item = orderItemsRepository.findById(lineItemId)
				.orElseThrow(() -> new InvalidDataException("Item not found"));

		if (item.getOrders() == null || item.getOrders().getOrderId() != orderId) {
			throw new InvalidDataException("Item does not belong to this order");
		}

		orderItemsRepository.delete(item);
	}

	// Mapper
	private OrderItemsResponseDto mapToResponse(OrderItems item) {
		OrderItemsResponseDto dto = new OrderItemsResponseDto();
		dto.setLineItemId(item.getLineItemId());
		dto.setOrderId(item.getOrders() != null ? item.getOrders().getOrderId() : 0);
		dto.setProductId(item.getProducts() != null ? item.getProducts().getProductId() : 0);
		dto.setQuantity(item.getQuantity());
		dto.setUnitPrice(item.getUnitPrice());
		return dto;
	}

	// Get all items by productId
	@Override
	public List<OrderItemsResponseDto> getItemsByProductId(int productId) {
		productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

		return orderItemsRepository.findItemsByProductId(productId).stream().map(this::mapToResponse)
				.toList();
	}

	// Get total quantity of a product across all orders
	@Override
	public Integer getTotalQuantityByProductId(int productId) {
		productsRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

		Integer total = orderItemsRepository.getTotalQuantityByProductId(productId);
		return total != null ? total : 0;
	}
}