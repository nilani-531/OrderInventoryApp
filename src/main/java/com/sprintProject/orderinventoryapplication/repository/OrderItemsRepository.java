package com.sprintProject.orderinventoryapplication.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sprintProject.orderinventoryapplication.entity.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {

	// Get items by order ID
	@Query("SELECT oi FROM OrderItems oi WHERE oi.orders.orderId = :orderId")
	List<OrderItems> findByOrderId(@Param("orderId") int orderId);

	// Bulk delete all order items for a given order (avoids Hibernate session conflict)
	@Modifying
	@Query("DELETE FROM OrderItems oi WHERE oi.orders.orderId = :orderId")
	void deleteAllByOrderId(@Param("orderId") int orderId);

	// Get all items by productId
	@Query("SELECT oi FROM OrderItems oi WHERE oi.products.productId = :productId")
	List<OrderItems> findItemsByProductId(@Param("productId") int productId);

	// Get total quantity of a product across all orders
	@Query("SELECT SUM(oi.quantity) FROM OrderItems oi WHERE oi.products.productId = :productId")
	Integer getTotalQuantityByProductId(@Param("productId") int productId);
}

