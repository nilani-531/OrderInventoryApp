package com.sprintProject.OrderInventoryApplication.EntityClasses;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Order_Items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int lineItemId;

   
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products products; 

    
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipments shipments;

    @NotNull
    @Min(1)
    private int quantity;
    @Column(name="unit_price")
    private double unitPrice;
	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public Products getProducts() {
		return products;
	}
	public void setProducts(Products products) {
		this.products = products;
	}
	public Shipments getShipments() {
		return shipments;
	}
	public void setShipments(Shipments shipments) {
		this.shipments = shipments;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
    
	
}