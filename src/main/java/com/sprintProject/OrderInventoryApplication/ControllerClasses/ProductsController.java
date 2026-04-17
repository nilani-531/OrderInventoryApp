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

import com.sprintProject.OrderInventoryApplication.dto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ProductsResponseDto;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.ProductsService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsService productsService;

	// Get all products
	@GetMapping
	public ResponseStructure<List<ProductsResponseDto>> getAllProducts() {
		return productsService.getAllProducts();
	}

	// Get product by ID
	@GetMapping("/{productId}")
	public  ResponseStructure<ProductsResponseDto> getProductById(@PathVariable int productId) {
		return productsService.getProductById(productId);
	}

	// Create new product
	@PostMapping
	public ResponseStructure<ProductsResponseDto> createProduct(@RequestBody ProductsRequestDto product) {
		return productsService.createProduct(product);
	}

	// Update product
	@PutMapping("/{productId}")
	public ResponseStructure<ProductsResponseDto> updateProduct(@PathVariable int productId, @RequestBody ProductsRequestDto product) {
		return productsService.updateProduct(productId, product);
	}

	// Delete product
	@DeleteMapping("/{productId}")
	public ResponseStructure<String> deleteProduct(@PathVariable int productId) {
		return productsService.deleteProduct(productId);
	}

}
