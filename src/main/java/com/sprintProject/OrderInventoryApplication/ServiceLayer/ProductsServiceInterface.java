package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;

public interface ProductsServiceInterface {
	
    List<Products> getAllProducts();
    Products getProductById(int productId);
    Products createProduct(Products product);
    Products updateProduct(int productId, Products product);
    void deleteProduct(int productId);
}


