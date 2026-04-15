package com.sprintProject.OrderInventoryApplication.RepositoryClasses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public interface ShipmentsRepository extends JpaRepository<Shipments, Integer>{
	List<Shipments> findByCustomer_CustomerId(Integer customerId);

    List<Shipments> findByStore_StoreId(Integer storeId);

    List<Shipments> findByShipmentStatus(ShipmentStatus shipmentStatus);

    boolean existsByShipmentId(Integer shipmentId);
}
