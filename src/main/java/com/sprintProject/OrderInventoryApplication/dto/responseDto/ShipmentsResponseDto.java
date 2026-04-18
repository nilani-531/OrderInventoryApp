package com.sprintProject.OrderInventoryApplication.dto.responseDto;

import java.util.Objects;

import com.sprintProject.OrderInventoryApplication.EntityClasses.ShipmentStatus;

public class ShipmentsResponseDto {

	private int shipmentId;
	private int customerId;
	private int storeId;
	private String deliveryAddress;
	private ShipmentStatus shipmentStatus;
	
	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getStoreId() {
		return storeId;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(customerId, deliveryAddress, shipmentId, shipmentStatus, storeId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentsResponseDto other = (ShipmentsResponseDto) obj;
		return customerId == other.customerId && Objects.equals(deliveryAddress, other.deliveryAddress)
				&& shipmentId == other.shipmentId && Objects.equals(shipmentStatus, other.shipmentStatus)
				&& storeId == other.storeId;
	}
	@Override
	public String toString() {
		return "ShipmentsResponseDto [shipmentId=" + shipmentId + ", customerId=" + customerId + ", storeId=" + storeId
				+ ", deliveryAddress=" + deliveryAddress + ", shipmentStatus=" + shipmentStatus + "]";
	}
	
}
