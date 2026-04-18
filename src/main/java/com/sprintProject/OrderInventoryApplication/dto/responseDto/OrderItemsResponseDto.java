package com.sprintProject.OrderInventoryApplication.dto.responseDto;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Products;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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

	 public int getQuantity() {
	     return quantity;
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
