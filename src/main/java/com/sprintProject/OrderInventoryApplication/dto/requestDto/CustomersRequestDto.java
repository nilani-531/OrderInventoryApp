package com.sprintProject.OrderInventoryApplication.dto.requestDto;

public class CustomersRequestDto {
	// DTO = Data Transfer Object → used to transfer data from client to server
	
	private String emailAddress;
	private String fullName;
	
    //Getters & Setters
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
