package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ProductsResponseDto;

public interface ProductsServiceInterface {
	
    ResponseStructure<List<ProductsResponseDto>> getAllProducts();
    ResponseStructure<ProductsResponseDto> getProductById(int productId);
    ResponseStructure<ProductsResponseDto> createProduct(ProductsRequestDto productRequestDto);
    ResponseStructure<ProductsResponseDto> updateProduct(int productId, ProductsRequestDto productRequestDto);
    ResponseStructure<String> deleteProduct(int productId);
}


