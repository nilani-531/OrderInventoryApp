package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ShipmentsRepository;


@Service
public class ShipmentsService implements ShipmentsServiceInterface{
	
	private ShipmentsRepository shipmentsRepository;

	public ShipmentsService(ShipmentsRepository shipmentsRepository) {
		this.shipmentsRepository = shipmentsRepository;
	}
	
	@Override
	public List<Shipments> getAllShipments(){
		return shipmentsRepository.findAll();
	}
    @Override
    public Shipments getShipmentById(int shipmentId) {
        return shipmentsRepository.findById(shipmentId).orElse(null);
    }
    @Override
    public Shipments createShipment(Shipments shipment) {
        return shipmentsRepository.save(shipment);
    }
    @Override
    public Shipments updateShipment(int shipmentId, Shipments data) {
        Shipments exists = shipmentsRepository.findById(shipmentId).orElse(null);
        if (exists == null) {
            return null;
        }
        if (data.getDeliveryAddress() != null) {
            exists.setDeliveryAddress(data.getDeliveryAddress());
        }
        if (data.getShipmentStatus() != null) {
            exists.setShipmentStatus(data.getShipmentStatus());
        }

        return shipmentsRepository.save(exists);
    }
    @Override
    public String deleteShipment(int shipmentId) {
        if (!shipmentsRepository.existsById(shipmentId)) {
            return "Shipment not found";
        }
        shipmentsRepository.deleteById(shipmentId);
        return "Shipment deleted successfully";
    }
    @Override
    public List<Shipments> getShipmentByCustomerId(int customerId) {
        return shipmentsRepository.findByCustomersCustomerId(customerId);
    }
    @Override
    public List<Shipments> getShipmentByStoreId(int storeId) {
        return shipmentsRepository.findByStoresStoreId(storeId);
    }
    @Override
    public List<Shipments> getShipmentByStatus(ShipmentStatus status) {
        return shipmentsRepository.findByShipmentStatus(status);
    }
}
