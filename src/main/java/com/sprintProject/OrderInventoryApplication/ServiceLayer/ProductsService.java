package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.ProductNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ProductsRepository;

<<<<<<< HEAD
=======
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ResponseStructure;
>>>>>>> master
import com.sprintProject.OrderInventoryApplication.dto.requestDto.ProductsRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ProductsResponseDto;

@Service
public class ProductsService implements ProductsServiceInterface{
	@Autowired
	private ProductsRepository productsRepository;

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

	@Override
<<<<<<< HEAD
	public List<ProductsResponseDto> getAllProducts() {
		return productsRepository.findAll().stream().map(this::mapToResponseDto).toList();
	}

	@Override
	public ProductsResponseDto getProductById(int productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
		return mapToResponseDto(product);
	}

	@Override
	public ProductsResponseDto createProduct(ProductsRequestDto productRequestDto) {
=======
	public ResponseStructure<List<ProductsResponseDto>> getAllProducts() {
		List<ProductsResponseDto> list = productsRepository.findAll().stream().map(this::mapToResponseDto).toList();
		ResponseStructure<List<ProductsResponseDto>> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Products fetched successfully");
		response.setData(list);
		return response;
	}

	@Override
	public ResponseStructure<ProductsResponseDto> getProductById(int productId) {
        Products product = productsRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
		ResponseStructure<ProductsResponseDto> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Product fetched successfully");
		response.setData(mapToResponseDto(product));
		return response;
	}

	@Override
	public ResponseStructure<ProductsResponseDto> createProduct(ProductsRequestDto productRequestDto) {
>>>>>>> master
		Products product = new Products();
		product.setProductName(productRequestDto.getProductName());
		product.setUnitPrice(productRequestDto.getUnitPrice());
		product.setColour(productRequestDto.getColour());
		product.setBrand(productRequestDto.getBrand());
		product.setSize(productRequestDto.getSize());
		product.setRating(productRequestDto.getRating());
		
		Products saved = productsRepository.save(product);
<<<<<<< HEAD
		return mapToResponseDto(saved);
	}

	@Override
	public ProductsResponseDto updateProduct(int productId, ProductsRequestDto productRequestDto) {
=======
		
		ResponseStructure<ProductsResponseDto> response = new ResponseStructure<>();
		response.setStatus(201);
		response.setMsg("Product created successfully");
		response.setData(mapToResponseDto(saved));
		return response;
	}

	@Override
	public ResponseStructure<ProductsResponseDto> updateProduct(int productId, ProductsRequestDto productRequestDto) {
>>>>>>> master
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
<<<<<<< HEAD
		return mapToResponseDto(updated);
	}

	@Override
	public String deleteProduct(int productId) {
=======
		ResponseStructure<ProductsResponseDto> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Product updated successfully");
		response.setData(mapToResponseDto(updated));
		return response;
	}

	@Override
	public ResponseStructure<String> deleteProduct(int productId) {
>>>>>>> master
		 Products product = productsRepository.findById(productId)
	                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

	        productsRepository.delete(product);
<<<<<<< HEAD
		return "Product deleted successfully with id: " + productId;
=======
		ResponseStructure<String> response = new ResponseStructure<>();
		response.setStatus(200);
		response.setMsg("Product deleted successfully");
		response.setData("Product deleted successfully with id: " + productId);
		return response;
>>>>>>> master
	}
}
