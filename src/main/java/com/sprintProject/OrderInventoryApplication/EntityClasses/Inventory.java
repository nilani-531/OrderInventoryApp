package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventory")
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventory_id;
	@ManyToOne
	@NotNull
	private Store store_id;
	@ManyToOne
	@NotNull
	private int produt_id;
	@NotNull
	private int product_inventory;
	
	
	public int getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}
	public int getStore_id() {
		return store_id;
	}
	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}
	public int getProdut_id() {
		return produt_id;
	}
	public void setProdut_id(int produt_id) {
		this.produt_id = produt_id;
	}
	public int getProduct_inventory() {
		return product_inventory;
	}
	public void setProduct_inventory(int product_inventory) {
		this.product_inventory = product_inventory;
	}
	

}
