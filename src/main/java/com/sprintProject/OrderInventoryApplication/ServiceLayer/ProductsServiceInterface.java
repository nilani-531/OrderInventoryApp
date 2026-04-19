package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ProductsResponseDto;

public interface ProductsServiceInterface {
	
	// Retrieves all products
    List<ProductsResponseDto> getAllProducts();
    // Retrieves a product by its ID
    ProductsResponseDto getProductById(int productId);
    // Creates a new product
    ProductsResponseDto createProduct(ProductsRequestDto productRequestDto);
    // Updates an existing product
    ProductsResponseDto updateProduct(int productId, ProductsRequestDto productRequestDto);
    // Deletes a product by its ID
    String deleteProduct(int productId);
}


