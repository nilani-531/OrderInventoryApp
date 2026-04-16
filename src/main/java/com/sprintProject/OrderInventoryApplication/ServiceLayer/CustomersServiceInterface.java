package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;

public interface CustomersServiceInterface {
   
	List<Customers> getAllCustomers();
	Customers getCustomerById(int customer_id);
	Customers createCustomer(Customers customers);
	Customers updateCustomer(int customer_id, Customers customers);
	void deleteCustomer(int customer_id);
	Customers getCustomerByEmail(String customer_email); 
	List<Orders> getCustomerOrders(int customer_id);
	List<Shipments> getCustomerShipments(int customer_id);
}
