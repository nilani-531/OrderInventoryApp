package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;
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
	private ProductsRepository productsRepository;

	// Get items by Order id
	@Override
	public List<OrderItemsResponseDto> getItemsByOrderId(int orderId) {
		return orderItemsRepository.findByOrderId(orderId).stream().map(this::mapToResponse)
				.toList();
	}

	// Add item to order
	@Override
	public OrderItemsResponseDto addItem(int orderId, int productId, OrderItemsRequestDto dto) {
		Orders order = ordersRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		Products product = productsRepository.findById(productId)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		if (dto.getQuantity() <= 0) {
			throw new InvalidDataException("Quantity must be greater than 0");
		}

		OrderItems item = new OrderItems();
		item.setOrders(order);
		item.setProducts(product);
		// BUG FIX: quantity was validated but never actually set on the item
		item.setQuantity(dto.getQuantity());
		item.setUnitPrice(dto.getUnitPrice());

		return mapToResponse(orderItemsRepository.save(item));
	}

	// Update item
	@Override
	public OrderItemsResponseDto updateItem(int orderId, int lineItemId, OrderItemsRequestDto dto) {
		OrderItems item = orderItemsRepository.findById(lineItemId)
				.orElseThrow(() -> new RuntimeException("Item not found"));

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
				.orElseThrow(() -> new RuntimeException("Item not found"));

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