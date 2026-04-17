package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.InvalidStatusTransitionException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ShipmentNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ShipmentsRepository;


@Service
public class ShipmentsService implements ShipmentsServiceInterface{
	@Autowired
	private ShipmentsRepository shipmentsRepository;

	@Override
	public List<Shipments> getAllShipments(){
		return shipmentsRepository.findAll();
	}
    @Override
    public Shipments getShipmentById(int shipmentId) {
    	
        Shipments shipment= shipmentsRepository.findById(shipmentId)
        		.orElseThrow(()-> new ShipmentNotFoundException("Shipment not found: "+shipmentId));
        
        return shipment;
    }
    @Override
    public Shipments createShipment(Shipments shipment) {
    	
    	if(shipment.getShipmentStatus()==null) {
    		shipment.setShipmentStatus(ShipmentStatus.CREATED);
    	}
    	
        return shipmentsRepository.save(shipment);
    }
    @Override
    public Shipments updateShipment(int shipmentId, Shipments shipmentUpdate) {
    	
        Shipments existingShipment = shipmentsRepository.findById(shipmentId)
        		.orElseThrow(()->new ShipmentNotFoundException("Shipment not found: "+shipmentId));
        
        if (shipmentUpdate.getDeliveryAddress() != null) {
            existingShipment.setDeliveryAddress(shipmentUpdate.getDeliveryAddress());
        }
        
        if (shipmentUpdate.getShipmentStatus() != null) {
        	ShipmentStatus current=existingShipment.getShipmentStatus();
        	ShipmentStatus next=shipmentUpdate.getShipmentStatus();
        	
        	if(!current.isValidTransition(next)) {
        		throw new InvalidStatusTransitionException("Invalid shipment transition: "+current+"-> "+next);
        	}
        	
            existingShipment.setShipmentStatus(next);
        }

        return shipmentsRepository.save(existingShipment);
    }
    @Override
    public void deleteShipment(int shipmentId) {
    	
    	if (!shipmentsRepository.existsById(shipmentId)) {
    	    throw new ShipmentNotFoundException("Shipment not found: " + shipmentId);
    	}
    	
    	shipmentsRepository.deleteById(shipmentId);
    }
    @Override
    public List<Shipments> getShipmentByCustomerId(int customerId) {
        return shipmentsRepository.findByCustomerCustomerId(customerId);
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
