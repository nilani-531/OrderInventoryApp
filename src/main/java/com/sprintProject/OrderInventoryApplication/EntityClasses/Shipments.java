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
	private int shipment_id;
	
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
	private String delivery_address;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="shipment_status",nullable=false, length=100)
	private ShipmentStatus shipment_status;
	
	@PrePersist
	public void setDefaultStatus() {
	    if (shipment_status == null) {
	        shipment_status = ShipmentStatus.CREATED;
	    }
	}
	
	public int getShipment_id() {
		return shipment_id;
	}
	public void setShipment_id(int shipment_id) {
		this.shipment_id = shipment_id;
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
	public String getDelivery_address() {
		return delivery_address;
	}
	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}
	public ShipmentStatus getShipment_status() {
		return shipment_status;
	}
	public void setShipment_status(ShipmentStatus shipment_status) {
		this.shipment_status = shipment_status;
	}
}
