package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ProductsRepository;

@Service
public class ProductsService implements ProductsServiceInterface{
	@Autowired
	private ProductsRepository productsRepository;

	@Override
	public List<Products> getAllProducts() {
		return productsRepository.findAll();
	}

	@Override
	public Products getProductById(int productId) {

        return productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
	}

	@Override
	public Products createProduct(Products product) {
		 return productsRepository.save(product);
	}

	@Override
	public Products updateProduct(int productId, Products product) {
		Products exProduct = productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (product.getProductName() != null)
        	exProduct.setProductName(product.getProductName());

        exProduct.setUnitPrice(product.getUnitPrice());
        exProduct.setRating(product.getRating());

        if (product.getColour() != null)
        	exProduct.setColour(product.getColour());

        if (product.getBrand() != null)
        	exProduct.setBrand(product.getBrand());

        if (product.getSize() != null)
        	exProduct.setSize(product.getSize());

        return productsRepository.save(exProduct);
	}

	@Override
	public void deleteProduct(int productId) {
		 Products product = productsRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

	        productsRepository.delete(product);
		
	}
}
