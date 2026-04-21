package com.sprintProject.OrderInventoryApplication.dto.requestDto;


public class InventoryRequestDto {
	private int inventoryId;
	
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

