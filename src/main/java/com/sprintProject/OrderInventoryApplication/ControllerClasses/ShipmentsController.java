package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.ShipmentsServiceInterface;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ShipmentsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentsController {
	
	@Autowired
    private ShipmentsServiceInterface shipmentsService;

	// Fetch all shipments
    @GetMapping
    public ResponseStructure<List<ShipmentsResponseDto>> getAllShipments() {
    	
        ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("All shipments fetched successfully");
        responseStructure.setData(shipmentsService.getAllShipments());

        return responseStructure;
    }

    // Fetch single shipment by ID 
    @GetMapping("/{shipmentId}")
    public ResponseStructure<ShipmentsResponseDto> getShipmentById(@PathVariable int shipmentId) {

        ResponseStructure<ShipmentsResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipment fetched successfully");
        // Calling service layer to get data
        responseStructure.setData(shipmentsService.getShipmentById(shipmentId));

        return responseStructure;
    }

    // Create new shipment
    @PostMapping
    public ResponseStructure<ShipmentsResponseDto> createShipment(@RequestBody ShipmentsRequestDto request) {

        ResponseStructure<ShipmentsResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(201);
        responseStructure.setMsg("Shipment created successfully");
        responseStructure.setData(shipmentsService.createShipment(request));

        return responseStructure;
    }

    // Update shipment details
    @PutMapping("/{shipmentId}")
    public ResponseStructure<ShipmentsResponseDto> updateShipment(@PathVariable int shipmentId, @RequestBody ShipmentsRequestDto request) {

        ResponseStructure<ShipmentsResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipment updated successfully");
        // Pass request DTO to service
        responseStructure.setData(shipmentsService.updateShipment(shipmentId, request));

        return responseStructure;
    }

    // Delete shipment
    @DeleteMapping("/{shipmentId}")
    public ResponseStructure<String> deleteShipment(@PathVariable int shipmentId) {

        ResponseStructure<String> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipment deleted successfully");
        responseStructure.setData(shipmentsService.deleteShipment(shipmentId));

        return responseStructure;
    }

    // Update only shipment status
    @PatchMapping("/{shipmentId}/shipmentStatus")
    public ResponseStructure<ShipmentsResponseDto> updateStatus(@PathVariable int shipmentId, @RequestParam ShipmentStatus shipmentStatus) {

        ResponseStructure<ShipmentsResponseDto> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipment status updated successfully");
        responseStructure.setData(shipmentsService.updateShipmentStatus(shipmentId, shipmentStatus));

        return responseStructure;
    }
    
    // Fetch all shipments belonging to a specific customer
    @GetMapping("/customer/{customerId}")
    public ResponseStructure<List<ShipmentsResponseDto>> getShipmentsByCustomer(@PathVariable int customerId) {

        ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipments fetched by customer");
        responseStructure.setData(shipmentsService.getShipmentByCustomerId(customerId));

        return responseStructure;
    }

    // Fetch all shipments handled by a specific store
    @GetMapping("/store/{storeId}")
    public ResponseStructure<List<ShipmentsResponseDto>> getShipmentsByStore(@PathVariable int storeId) {

        ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipments fetched by store");
        responseStructure.setData(shipmentsService.getShipmentByStoreId(storeId));

        return responseStructure;
    }

    // Fetch shipments based on their current status
    @GetMapping("/status/{shipmentStatus}")
    public ResponseStructure<List<ShipmentsResponseDto>> getShipmentsByStatus(@PathVariable ShipmentStatus shipmentStatus) {

        ResponseStructure<List<ShipmentsResponseDto>> responseStructure = new ResponseStructure<>();
        responseStructure.setStatus(200);
        responseStructure.setMsg("Shipments fetched by status");
        responseStructure.setData(shipmentsService.getShipmentByStatus(shipmentStatus));

        return responseStructure;
    }
}