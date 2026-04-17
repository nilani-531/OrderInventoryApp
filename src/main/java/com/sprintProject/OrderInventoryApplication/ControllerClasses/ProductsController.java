package com.sprintProject.OrderInventoryApplication.ControllerClasses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Inventory;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {
	
	@Autowired
	private ProductsService productsService;
	
	@GetMapping
    public List<Products> getAllProducts() {
        return productsService.getAllProducts();
    }
	
	@PostMapping
    public Products createProduct(@RequestBody Products product) {
        return productsService.createProduct(product);
    }

}
