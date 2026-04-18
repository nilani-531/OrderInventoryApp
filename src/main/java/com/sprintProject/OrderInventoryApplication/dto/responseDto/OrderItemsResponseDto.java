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
	 public double getUnitPrice() {
		 return unitPrice;
	 }
	 public void setUnitPrice(double unitPrice) {
		 this.unitPrice = unitPrice;
	 }
	 @Override
	 public String toString() {
		return "OrderItemsResponseDto [lineItemId=" + lineItemId +  ", unitPrice=" + unitPrice + "]";
	 }
	 
}
