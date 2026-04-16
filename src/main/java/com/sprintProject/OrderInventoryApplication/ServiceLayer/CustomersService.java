package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerIdNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailAlreadyExistException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;

@Service
public class CustomersService implements CustomersServiceInterface{

	@Autowired
	private CustomersRepository customersRepository;
	
	public List<Customers> getAllCustomers(){
		return customersRepository.findAll();
	}
	
	public Customers getCustomerById(int customerId) {
		return customersRepository.findById(customerId).orElseThrow(() -> new CustomerIdNotFoundException("Customer not found with id: " + customerId));
	}
	
	public Customers createCustomer(Customers customers) {
		if (customersRepository.existsByEmailAddress(customers.getEmailAddress())) {
		    throw new CustomerEmailAlreadyExistException("Customer email already exists ");
		}
		return customersRepository.save(customers);
	}
	
	public Customers updateCustomer(int customerId, Customers customers) {
	    Customers existingCustomer = getCustomerById(customerId);
	    if (!existingCustomer.getEmailAddress().equals(customers.getEmailAddress()) &&
	        customersRepository.existsByEmailAddress(customers.getEmailAddress())) {
	        throw new CustomerEmailAlreadyExistException("Customer email already exists");
	    }
	    existingCustomer.setFullName(customers.getFullName());
	    existingCustomer.setEmailAddress(customers.getEmailAddress());
	    return customersRepository.save(existingCustomer);
	}
	
	public void deleteCustomer(int customerId) {
		Customers existingCustomer=getCustomerById(customerId);
		customersRepository.delete(existingCustomer);
	}
	public Customers getCustomerByEmail(String customerEmail) {
	    return customersRepository.findByEmailAddress(customerEmail).orElseThrow(() -> new CustomerEmailNotFoundException( "Customer not found with email: " + customerEmail));
	}

	public List<Orders> getCustomerOrders (int customerId){
		Customers existingCustomer= getCustomerById(customerId);
		return existingCustomer.getOrders();
	}
	public List<Shipments> getCustomerShipments(int customerId){
		Customers existingCustomer=getCustomerById(customerId);
		return existingCustomer.getShipments();
	}

	

	

}
