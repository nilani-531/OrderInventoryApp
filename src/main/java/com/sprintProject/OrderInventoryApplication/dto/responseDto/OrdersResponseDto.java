package com.sprintProject.OrderInventoryApplication.dto.responseDto;

import java.time.LocalDateTime;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Stores;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class OrdersResponseDto {
	private int orderId;

	private Customers customers;

	private Stores stores;

	private OrderStatus orderStatusS;

	private LocalDateTime orderTms;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public OrderStatus getOrderStatusS() {
		return orderStatusS;
	}

	public void setOrderStatusS(OrderStatus orderStatusS) {
		this.orderStatusS = orderStatusS;
	}

	public LocalDateTime getOrderTms() {
		return orderTms;
	}

	public void setOrderTms(LocalDateTime orderTms) {
		this.orderTms = orderTms;
	}

	@Override
	public String toString() {
		return "OrdersResponseDto [orderId=" + orderId + ", customers=" + customers + ", stores=" + stores
				+ ", orderStatusS=" + orderStatusS + ", orderTms=" + orderTms + "]";
	}
	
	
	

}
