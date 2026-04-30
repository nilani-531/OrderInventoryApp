package com.sprintProject.orderinventoryapplication.controller;

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

import com.sprintProject.orderinventoryapplication.dto.responseDto.ResponseStructure;
import com.sprintProject.orderinventoryapplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ProductsResponseDto;
import com.sprintProject.orderinventoryapplication.service.ProductsServiceInterface;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins = "http://localhost:4200/")
public class ProductsController {

	@Autowired
	private ProductsServiceInterface productsServiceInterface;

	// Get all products
	@GetMapping
	public ResponseStructure<List<ProductsResponseDto>> getAllProducts() {
		List<ProductsResponseDto> list = productsServiceInterface.getAllProducts();
        ResponseStructure<List<ProductsResponseDto>> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Products fetched successfully");
        rs.setData(list);
		return rs;
	}

	// Get product by ID
	@GetMapping("/{productId}")
	public  ResponseStructure<ProductsResponseDto> getProductById(@PathVariable Integer productId) {
		ProductsResponseDto response = productsServiceInterface.getProductById(productId);
        ResponseStructure<ProductsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Product fetched successfully");
        rs.setData(response);
		return rs;
	}

	// Create new product
	@PostMapping
	public ResponseStructure<ProductsResponseDto> createProduct(@Valid @RequestBody ProductsRequestDto product) {
		ProductsResponseDto response = productsServiceInterface.createProduct(product);
        ResponseStructure<ProductsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(201);
        rs.setMsg("Product created successfully");
        rs.setData(response);
		return rs;
	}

	// Update product
	@PutMapping("/{productId}")
	public ResponseStructure<ProductsResponseDto> updateProduct(@PathVariable Integer productId,@Valid @RequestBody ProductsRequestDto product) {
		ProductsResponseDto response = productsServiceInterface.updateProduct(productId, product);
        ResponseStructure<ProductsResponseDto> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Product updated successfully");
        rs.setData(response);
		return rs;
	}

	// Delete product
	@DeleteMapping("/{productId}")
	public ResponseStructure<String> deleteProduct(@PathVariable Integer productId) {
		productsServiceInterface.deleteProduct(productId);
        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatus(200);
        rs.setMsg("Product deleted successfully");
        rs.setData("Product deleted successfully with id: " + productId);
		return rs;
	}

}


