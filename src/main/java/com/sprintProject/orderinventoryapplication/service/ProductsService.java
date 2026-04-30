package com.sprintProject.orderinventoryapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.orderinventoryapplication.customexception.ProductNotFoundException;
import com.sprintProject.orderinventoryapplication.entity.Products;
import com.sprintProject.orderinventoryapplication.repository.ProductsRepository;

import com.sprintProject.orderinventoryapplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.ProductsResponseDto;

@Service
public class ProductsService implements ProductsServiceInterface{
	@Autowired
	private ProductsRepository productsRepository;

	// Maps a Products entity to a ProductsResponseDto
	private ProductsResponseDto mapToResponseDto(Products product) {
		ProductsResponseDto dto = new ProductsResponseDto();
		dto.setProductId(product.getProductId());
		dto.setProductName(product.getProductName());
		dto.setUnitPrice(product.getUnitPrice());
		dto.setColour(product.getColour());
		dto.setBrand(product.getBrand());
		dto.setSize(product.getSize());
		dto.setRating(product.getRating());
		return dto;
	}

	// Retrieves all products
	@Override
	public List<ProductsResponseDto> getAllProducts() {
		return productsRepository.findAll().stream().map(this::mapToResponseDto).toList();
	}

	// Retrieves a product by its ID
	@Override
	public ProductsResponseDto getProductById(Integer productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
		return mapToResponseDto(product);
	}

	// Creates a new product
	@Override
	public ProductsResponseDto createProduct(ProductsRequestDto productRequestDto) {
		Products product = new Products();
		product.setProductName(productRequestDto.getProductName());
		product.setUnitPrice(productRequestDto.getUnitPrice());
		product.setColour(productRequestDto.getColour());
		product.setBrand(productRequestDto.getBrand());
		product.setSize(productRequestDto.getSize());
		product.setRating(productRequestDto.getRating());
		
		Products saved = productsRepository.save(product);
		return mapToResponseDto(saved);
	}

	// Updates an existing product
	@Override
	public ProductsResponseDto updateProduct(Integer productId, ProductsRequestDto productRequestDto) {
		Products exProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        if (productRequestDto.getProductName() != null)
        	exProduct.setProductName(productRequestDto.getProductName());

        exProduct.setUnitPrice(productRequestDto.getUnitPrice());
        exProduct.setRating(productRequestDto.getRating());

        if (productRequestDto.getColour() != null)
        	exProduct.setColour(productRequestDto.getColour());

        if (productRequestDto.getBrand() != null)
        	exProduct.setBrand(productRequestDto.getBrand());

        if (productRequestDto.getSize() != null)
        	exProduct.setSize(productRequestDto.getSize());

        Products updated = productsRepository.save(exProduct);
		return mapToResponseDto(updated);
	}

	// Deletes a product by its ID
	@Override
	public String deleteProduct(Integer productId) {
		 Products product = productsRepository.findById(productId)
	                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

	        productsRepository.delete(product);
		return "Product deleted successfully with id: " + productId;
	}
}


