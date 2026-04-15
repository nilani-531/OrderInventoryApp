package com.sprintProject.OrderInventoryApplication.EntityClasses;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class Order_Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int lineItemId;

   
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product; 

    
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipments shipment;

    @NotNull
    @Min(1)
    private int quantity;

	public int getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Orders getOrder() 
	{
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Products getProduct() {
		return product;
	}

	public void setProduct(Products product) {
		this.product = product;
	}

	public Shipments getShipment() {
		return shipment;
	}

	public void setShipment(Shipments shipment) {
		this.shipment = shipment;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    
}