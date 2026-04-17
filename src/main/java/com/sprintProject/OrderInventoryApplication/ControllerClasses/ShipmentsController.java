package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.OrderInventoryApplication.ServiceLayer.ShipmentsServiceInterface;


@RestController
@RequestMapping("/api/shipments")
public class ShipmentsController {
	@Autowired
	private ShipmentsServiceInterface shipmentService;

}
