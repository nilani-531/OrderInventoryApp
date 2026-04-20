package com.sprintProject.OrderInventoryApplication.EntityClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
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
	@JsonIgnore
	@JoinColumn(name="store_id", nullable=false)
	private Stores stores;
	
	@NotNull
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="customer_id", nullable=false)
	private Customers customers;
	
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

	public Stores getStores() {
		return stores;
	}

	public void setStores(Stores stores) {
		this.stores = stores;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
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
