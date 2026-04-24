package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	

	// GET /api/orders/customer/{customerId}
	@Query("SELECT o FROM Orders o WHERE o.customers.customerId = :customerId")
	List<Orders> findByCustomerId(@Param("customerId") int customerId);

	// GET /api/orders/store/{storeId}
	@Query("SELECT o FROM Orders o WHERE o.stores.storeId = :storeId")
	List<Orders> findByStoreId(@Param("storeId") int storeId);

	// GET /api/orders/status/{status}
	@Query("SELECT o FROM Orders o WHERE o.orderStatusS = :status")
	List<Orders> findByStatus(@Param("status") OrderStatus status);

	// GET /api/orders/date-range?from=&to=
	@Query("SELECT o FROM Orders o WHERE o.orderTms >= :start AND o.orderTms <= :end")
	List<Orders> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	// Count by status (existing)
	@Query("SELECT COUNT(o) FROM Orders o WHERE o.orderStatusS = :status")
	long countOrdersByStatus(@Param("status") OrderStatus status);
}