package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

// To create the database table.
@Entity
public class Customer {
	//To identify the key as primary key.
    @Id
    //To generate the id automatically.
    @GeneratedValue(strategy=GenerationType.IDENTITY)
       private int customer_id;
    
    //To ensures the data is not null, not empty, not just space
    @NotBlank
	private String email_address;
    
    @NotBlank
	private String full_name;
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
}
