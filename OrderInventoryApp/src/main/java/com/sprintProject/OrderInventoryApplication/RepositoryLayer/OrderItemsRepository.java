package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
	
	 // Get all items by productId
    @Query("SELECT oi FROM OrderItems oi WHERE oi.products.productId = :productId")
    List<OrderItems> findItemsByProductId(@Param("productId") int productId);


    //  Get total quantity of a product across all orders
    @Query("SELECT SUM(oi.quantity) FROM OrderItems oi WHERE oi.products.productId = :productId")
    Integer getTotalQuantityByProductId(@Param("productId") int productId);
}
