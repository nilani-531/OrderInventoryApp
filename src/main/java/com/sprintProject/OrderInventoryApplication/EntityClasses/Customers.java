
package com.sprintProject.OrderInventoryApplication.EntityClasses;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;


@Entity
//@Entity ->Marks this class as a database table
public class Customers {
	
    @Id  
    // Primary key
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    // Auto-increment ID in database
    @Column(name="customer_id")
    // Column name in DB
        
    private int customerId;
    
    @NotBlank
    // Field cannot be empty
    @Email
    // Must be valid email format
    @Column(name="email_address")
	
    private String emailAddress;
    
    @NotBlank
    @Column(name="full_name")
	 
    private String fullName;
    

    @OneToMany (mappedBy = "customers", cascade = CascadeType.ALL)
    // One customer → many orders
    
    private List<Orders> orders;
    
    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL)
    // One customer → many orders
    
    private List<Shipments> shipments;
    
	//Getters & Setters
    
    public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public List<Shipments> getShipments() {
		return shipments;
	}
	public void setShipments(List<Shipments> shipments) {
		this.shipments = shipments;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
}
