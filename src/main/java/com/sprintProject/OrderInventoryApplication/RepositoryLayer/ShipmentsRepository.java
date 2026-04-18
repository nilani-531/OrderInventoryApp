package com.sprintProject.OrderInventoryApplication.RepositoryLayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public interface ShipmentsRepository extends JpaRepository<Shipments,Integer>{
	List<Shipments> findByCustomersCustomerId(Integer customerId);
	List<Shipments> findByStoresStoreId(Integer storeId);
	List<Shipments> findByShipmentStatus(ShipmentStatus status);
}
