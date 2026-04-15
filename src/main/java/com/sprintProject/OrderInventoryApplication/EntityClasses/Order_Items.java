package com.sprintProject.OrderInventoryApplication.EntityClasses;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "product_id"}))
public class Order_Items 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "line_item_id")
    private int lineItemId;

    // 🔗 Many items belong to one order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    // 🔗 Each item refers to one product
    @NotNull
    @Column(name = "product_id")
    private Products productId;

    // 🔥 Quantity must be > 0
    @NotNull
    @Min(1)
    private int quantity;

    // 🔥 Price should not change after shipment (logic later)
    @NotNull
    @Column(name = "unit_price")
    private double unitPrice;

    @NotNull
    @Column(name = "shipment_id")
    private Shipments shipmentId;

    public int getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public Products getProductId() {
		return productId;
	}

	public void setProductId(Products productId) {
		this.productId = productId;
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

	public Shipments getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Shipments shipmentId) {
		this.shipmentId = shipmentId;
	}

	}