package com.sprintProject.OrderInventoryApplication.EntityClasses;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Order_Items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class Order_Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int line_item_id;

   
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order_id;

    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Products product_id; 

    
    @ManyToOne
    @JoinColumn(name = "shipment_id")
    private Shipments shipment_id;

    @NotNull
    @Min(1)
    private int quantity;
    @Column(name="unit_price")
    private double unit_price;
	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public int getLineItemId() {
		return line_item_id;
	}

	public void setLineItemId(int lineItemId) {
		this.line_item_id = lineItemId;
	}

	public Orders getOrderId() 
	{
		return order_id;
	}

	public void setOrderId(Orders order_id) {
		this.order_id = order_id;
	}

	public Products getProductId() {
		return product_id;
	}

	public void setProductId(Products product_id) {
		this.product_id = product_id;
	}

	public Shipments getShipmentId() {
		return shipment_id;
	}

	public void setShipmentId(Shipments shipment_id) {
		this.shipment_id = shipment_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

    
}