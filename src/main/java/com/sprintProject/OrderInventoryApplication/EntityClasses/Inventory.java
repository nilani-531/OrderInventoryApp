package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Inventory")
public class Inventory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int inventory_id;
	@ManyToOne
	@JoinColumn(name="store_id")
	@NotNull
	private Stores store_id;
	@ManyToOne
	@JoinColumn(name="product_id")
	@NotNull
	private int product_id;
	@NotNull
	private int product_inventory;
	public int getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}
	public Stores getStore_id() {
		return store_id;
	}
	public void setStore_id(Stores store_id) {
		this.store_id = store_id;
	}
	public int getProdut_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getProduct_inventory() {
		return product_inventory;
	}
	public void setProduct_inventory(int product_inventory) {
		this.product_inventory = product_inventory;
	}
	
	

}
