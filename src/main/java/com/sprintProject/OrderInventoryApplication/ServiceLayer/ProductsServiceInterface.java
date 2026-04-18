package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ProductsResponseDto;

public interface ProductsServiceInterface {
	
    List<ProductsResponseDto> getAllProducts();
    ProductsResponseDto getProductById(int productId);
    ProductsResponseDto createProduct(ProductsRequestDto productRequestDto);
    ProductsResponseDto updateProduct(int productId, ProductsRequestDto productRequestDto);
    String deleteProduct(int productId);
}


