package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public interface ShipmentsRepository extends JpaRepository<Shipments,Integer>{
	
	@Query("SELECT s FROM Shipments s WHERE s.customers.customerId = :customerId")
	List<Shipments> findByCustomersCustomerId(@Param("customerId") int customerId);
	
	@Query("SELECT s FROM Shipments s WHERE s.stores.storeId = :storeId")
	List<Shipments> findByStoresStoreId(@Param("storeId") int storeId);
	
	@Query("SELECT s FROM Shipments s WHERE s.shipmentStatus = :status")
	List<Shipments> findByShipmentStatus(@Param("status") ShipmentStatus status);
}
