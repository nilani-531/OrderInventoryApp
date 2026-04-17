package com.sprintProject.OrderInventoryApplication.dto.responseDto;

<<<<<<< HEAD
public class InventoryResponseDto {

	private int inventoryId;

	private int productInventory;

=======
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

public class InventoryResponseDto {

	private int inventoryId;
	
	private Stores stores;
	
	private Products products;
	
	private int productInventory;	
	
	
	
>>>>>>> master
	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

<<<<<<< HEAD
=======
	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

>>>>>>> master
	public int getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(int productInventory) {
		this.productInventory = productInventory;
	}

<<<<<<< HEAD
=======

>>>>>>> master
}
