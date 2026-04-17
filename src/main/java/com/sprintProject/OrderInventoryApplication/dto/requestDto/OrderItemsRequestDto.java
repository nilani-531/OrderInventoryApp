package com.sprintProject.OrderInventoryApplication.dto.requestDto;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public class OrderItemsRequestDto {
	 private int lineItemId;
     private Orders orders;	    
	 private Products products; 
     private Shipments shipments;
     private double unitPrice;
     private int quantity;

     public int getQuantity() {
         return quantity;
     }

     public void setQuantity(int quantity) {
         this.quantity = quantity;
     }
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
	 public double getUnitPrice() {
		 return unitPrice;
	 }
	 public void setUnitPrice(double unitPrice) {
		 this.unitPrice = unitPrice;
	 }
}
