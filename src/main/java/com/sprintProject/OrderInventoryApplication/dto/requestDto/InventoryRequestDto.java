package com.sprintProject.OrderInventoryApplication.dto.requestDto;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

public class InventoryRequestDto {
	private int inventoryId;

	
	private Stores stores;
	
	private Products products;
	
	private int productInventory;
	
	


	

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(int productInventory) {
		this.productInventory = productInventory;
	}

}

