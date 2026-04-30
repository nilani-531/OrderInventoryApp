package com.sprintProject.orderinventoryapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sprintProject.orderinventoryapplication.entity.ShipmentStatus;
import com.sprintProject.orderinventoryapplication.entity.Shipments;

public interface ShipmentsRepository extends JpaRepository<Shipments,Integer>{
	
	// Get shipments by customer ID
	@Query("SELECT s FROM Shipments s WHERE s.customers.customerId = :customerId")
	List<Shipments> findByCustomersCustomerId(@Param("customerId") Integer customerId);
	
	// Get shipments by store ID
	@Query("SELECT s FROM Shipments s WHERE s.stores.storeId = :storeId")
	List<Shipments> findByStoresStoreId(@Param("storeId") Integer storeId);
	
	// Get shipments by status
	@Query("SELECT s FROM Shipments s WHERE s.shipmentStatus = :status")
	List<Shipments> findByShipmentStatus(@Param("status") ShipmentStatus status);
}