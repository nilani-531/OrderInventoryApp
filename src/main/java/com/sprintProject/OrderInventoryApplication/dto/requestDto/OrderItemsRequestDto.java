package com.sprintProject.OrderInventoryApplication.dto.requestDto;

public class OrderItemsRequestDto {

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