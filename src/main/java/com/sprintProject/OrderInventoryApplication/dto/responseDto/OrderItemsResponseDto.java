package com.sprintProject.OrderInventoryApplication.dto.responseDto;

public class OrderItemsResponseDto {

    private int lineItemId;


    private int orderId;
    private int productId;

    private int quantity;
    private double unitPrice;

    public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

public class OrderItemsResponseDto {
	 private int lineItemId;

     private Orders orders;	    
	 private Products products; 
     private Shipments shipments;

     private double unitPrice;
	 public int getLineItemId() {
		 return lineItemId;
	 }
	 private int quantity;


    public double getUnitPrice() {
        return unitPrice;
    }


    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

 public void setQuantity(int quantity) {
	     this.quantity = quantity;
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

	 public double getUnitPrice() {
		 return unitPrice;
	 }
	 public void setUnitPrice(double unitPrice) {
		 this.unitPrice = unitPrice;
	 }
	 @Override
	 public String toString() {

		return "OrderItemsResponseDto [lineItemId=" + lineItemId + ", orders=" + orders + ", products=" + products
				+ ", shipments=" + shipments + ", unitPrice=" + unitPrice + "]";

	 }
	 
}

