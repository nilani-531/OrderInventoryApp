package com.sprintProject.OrderInventoryApplication.dto.requestDto;

public class OrderItemsRequestDto {


	 private int lineItemId;
     private Orders orders;	    
	 private Products products; 
     private Shipments shipments;

     private double unitPrice;
     private int quantity;


    private int quantity;
    private double unitPrice;

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

     public void setQuantity(int quantity) {
         this.quantity = quantity;
     }
	 public int getLineItemId() {
		 return lineItemId;
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
}

