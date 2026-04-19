package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailAlreadyExistException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerIdNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;

@Service
//Marks this class as Service layer (business logic)

public class CustomersService implements CustomersServiceInterface {

    @Autowired
    private CustomersRepository customersRepository;

    // Get all Customers
    public List<CustomersResponseDto> getAllCustomers() {
        return customersRepository.findAll().stream().map(this::convertToResponseDto).toList();
    }

    // Get By CustomersId
    public CustomersResponseDto getCustomerById(int customerId) {
        Customers customers= customersRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(
                        "Customer not found with id: " + customerId));
      return convertToResponseDto(customers);
    }

    //Create the Customer
    public CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto) {

        if (customersRepository.existsByEmailAddress(customersRequestDto.getEmailAddress())) {
            throw new CustomerEmailAlreadyExistException("Customer email already exists");
        }
        // Validation: prevent duplicate email
            
        Customers customers=new Customers();
        customers.setFullName(customersRequestDto.getFullName());
        customers.setEmailAddress(customersRequestDto.getEmailAddress());
        // Convert DTO → Entity
        
        Customers newCustomers= customersRepository.save(customers);
        // Save to DB
        
        return convertToResponseDto(newCustomers);

    }

    // update the Customer
    public CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto) {

        Customers existingCustomer = customersRepository.findById(customerId).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with id: "+customerId));

        if (!existingCustomer.getEmailAddress().equals(customersRequestDto.getEmailAddress()) &&
                customersRepository.existsByEmailAddress(customersRequestDto.getEmailAddress())) {

            throw new CustomerEmailAlreadyExistException("Customer email already exists");
        }
        // Check duplicate email only if changed

        existingCustomer.setFullName(customersRequestDto.getFullName());
        existingCustomer.setEmailAddress(customersRequestDto.getEmailAddress());

        Customers updatedCustomer= customersRepository.save(existingCustomer);
        return convertToResponseDto(updatedCustomer);
    }

    // Delete the Customer
    public String deleteCustomer(int customerId) {
        Customers existingCustomer=customersRepository.findById(customerId).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with id:"+customerId));
        customersRepository.delete(existingCustomer);
        return "Account Removed successfully";

    }
   
    // Get Customer By Email
    public CustomersResponseDto  getCustomerByEmail(String customerEmail) {
       Customers customers= customersRepository.findByEmailAddress(customerEmail)
                .orElseThrow(() -> new CustomerEmailNotFoundException(
                        "Customer not found with email: " + customerEmail));
     return convertToResponseDto(customers);

    }

   // Fetch orders from relationship
    public List<Orders> getCustomerOrders(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId));
        return existingCustomer.getOrders();
    }

    // Fetch shipments from relationship
    public List<Shipments> getCustomerShipments(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId)); 
        return existingCustomer.getShipments();
    }
    
    // ResponseDto conversion
    private CustomersResponseDto convertToResponseDto(Customers customers) {
    	CustomersResponseDto customersResponseDto=new CustomersResponseDto();
    	customersResponseDto.setCustomerId(customers.getCustomerId());
    	customersResponseDto.setEmailAddress(customers.getEmailAddress());
    	customersResponseDto.setFullName(customers.getFullName());
    	return customersResponseDto;
    }



	
    
}