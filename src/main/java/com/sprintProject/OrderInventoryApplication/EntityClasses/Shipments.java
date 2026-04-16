package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="Shipments")
public class Shipments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="shipment_id")
	private int shipmentId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="store_id", nullable=false)
	private Stores store;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="customer_id", nullable=false)
	private Customers customer;
	
	@NotBlank
	@Column(name="delivery_address", nullable=false, length=512)
	private String deliveryAddress;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="shipment_status",nullable=false, length=100)
	private ShipmentStatus shipmentStatus;
	
	@PrePersist
	public void setDefaultStatus() {
	    if (shipmentStatus == null) {
	        shipmentStatus = ShipmentStatus.CREATED;
	    }
	}

	public int getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}
	public Stores getStore() {
		return store;
	}
	public void setStore(Stores store) {
		this.store = store;
	}
	public Customers getCustomer() {
		return customer;
	}
	public void setCustomer(Customers customer) {
		this.customer = customer;
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
}
