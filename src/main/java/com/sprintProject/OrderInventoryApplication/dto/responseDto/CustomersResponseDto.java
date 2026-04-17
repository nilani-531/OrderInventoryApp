package com.sprintProject.OrderInventoryApplication.dto.responseDto;

import java.util.Objects;

public class CustomersResponseDto {

	 private int customerId;
	 private String emailAddress;
	 private String fullName;
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
	@Override
	public int hashCode() {
		return Objects.hash(customerId, emailAddress, fullName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomersResponseDto other = (CustomersResponseDto) obj;
		return customerId == other.customerId && Objects.equals(emailAddress, other.emailAddress)
				&& Objects.equals(fullName, other.fullName);
	}
	@Override
	public String toString() {
		return "CustomersResponseDto [customerId=" + customerId + ", emailAddress=" + emailAddress + ", fullName="
				+ fullName + "]";
	}
	 
	 
	 
}
