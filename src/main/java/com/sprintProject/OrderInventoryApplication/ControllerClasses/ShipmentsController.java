package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public ResponseStructure<List<ShipmentsResponseDto>> getAllShipments() {

        ResponseStructure<List<ShipmentsResponseDto>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("All shipments fetched successfully");
        response.setData(shipmentsService.getAllShipments());

        return response;
    }

    @GetMapping("/{shipmentId}")
    public ResponseStructure<ShipmentsResponseDto> getShipmentById(@PathVariable int shipmentId) {

        ResponseStructure<ShipmentsResponseDto> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipment fetched successfully");
        response.setData(shipmentsService.getShipmentById(shipmentId));

        return response;
    }

    @PostMapping
    public ResponseStructure<ShipmentsResponseDto> createShipment(@RequestBody ShipmentsRequestDto request) {

        ResponseStructure<ShipmentsResponseDto> response = new ResponseStructure<>();
        response.setStatus(201);
        response.setMsg("Shipment created successfully");
        response.setData(shipmentsService.createShipment(request));

        return response;
    }


    @PutMapping("/{shipmentId}")
    public ResponseStructure<ShipmentsResponseDto> updateShipment(@PathVariable int shipmentId, @RequestBody ShipmentsRequestDto request) {

        ResponseStructure<ShipmentsResponseDto> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipment updated successfully");
        response.setData(shipmentsService.updateShipment(shipmentId, request));

        return response;
    }

    @DeleteMapping("/{shipmentId}")
    public ResponseStructure<String> deleteShipment(@PathVariable int shipmentId) {

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipment deleted successfully");
        response.setData(shipmentsService.deleteShipment(shipmentId));

        return response;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseStructure<List<Shipments>> getShipmentsByCustomer(@PathVariable int customerId) {

        ResponseStructure<List<Shipments>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipments fetched by customer");
        response.setData(shipmentsService.getShipmentByCustomerId(customerId));

        return response;
    }

    @GetMapping("/store/{storeId}")
    public ResponseStructure<List<Shipments>> getShipmentsByStore(@PathVariable int storeId) {

        ResponseStructure<List<Shipments>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipments fetched by store");
        response.setData(shipmentsService.getShipmentByStoreId(storeId));

        return response;
    }

    @GetMapping("/status/{shipmentStatus}")
    public ResponseStructure<List<Shipments>> getShipmentsByStatus(@PathVariable ShipmentStatus shipmentStatus) {

        ResponseStructure<List<Shipments>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMsg("Shipments fetched by status");
        response.setData(shipmentsService.getShipmentByStatus(shipmentStatus));

        return response;
    }
}
