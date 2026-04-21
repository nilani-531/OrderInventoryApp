package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.time.LocalDateTime;
import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	
	  // Get orders count by status
	  @Query("SELECT COUNT(o) FROM Orders o WHERE o.orderStatusS = :status")
	    long countOrdersByStatus(@Param("status") OrderStatus status);

	@Query("SELECT o FROM Orders o WHERE o.stores.storeId = :storeId")
	List<Orders> getOrdersByStore(@Param("storeId") int storeId);
}