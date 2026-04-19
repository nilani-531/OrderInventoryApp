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
public class CustomersService implements CustomersServiceInterface {

    @Autowired
    private CustomersRepository customersRepository;

  
    public List<CustomersResponseDto> getAllCustomers() {
        return customersRepository.findAll().stream().map(this::convertToResponseDto).toList();
    }

  
    public CustomersResponseDto getCustomerById(int customerId) {
        Customers customers= customersRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(
                        "Customer not found with id: " + customerId));
      return convertToResponseDto(customers);
//        CustomersResponseDto customersResponseDto=new CustomersResponseDto();
//        customersResponseDto.setCustomerId(customers.getCustomerId());
//        customersResponseDto.setFullName(customers.getFullName());
//        customersResponseDto.setEmailAddress(customers.getEmailAddress());
//        return customersResponseDto;
    }

    
    public CustomersResponseDto createCustomer(CustomersRequestDto customersRequestDto) {

        if (customersRepository.existsByEmailAddress(customersRequestDto.getEmailAddress())) {
            throw new CustomerEmailAlreadyExistException("Customer email already exists");
        }
            
        Customers customers=new Customers();
        customers.setFullName(customersRequestDto.getFullName());
        customers.setEmailAddress(customersRequestDto.getEmailAddress());
        Customers newCustomers= customersRepository.save(customers);
        return convertToResponseDto(newCustomers);
//        CustomersResponseDto customersResponseDto=new CustomersResponseDto();
//        customersResponseDto.setCustomerId(newCustomers.getCustomerId());
//        customersResponseDto.setFullName(newCustomers.getFullName());
//        customersResponseDto.setEmailAddress(newCustomers.getEmailAddress());
//        return customersResponseDto;
    }

 
    public CustomersResponseDto updateCustomer(int customerId, CustomersRequestDto customersRequestDto) {

        Customers existingCustomer = customersRepository.findById(customerId).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with id: "+customerId));

        if (!existingCustomer.getEmailAddress().equals(customersRequestDto.getEmailAddress()) &&
                customersRepository.existsByEmailAddress(customersRequestDto.getEmailAddress())) {

            throw new CustomerEmailAlreadyExistException("Customer email already exists");
        }

        existingCustomer.setFullName(customersRequestDto.getFullName());
        existingCustomer.setEmailAddress(customersRequestDto.getEmailAddress());

        Customers updatedCustomer= customersRepository.save(existingCustomer);
        return convertToResponseDto(updatedCustomer);
//        CustomersResponseDto customersResponseDto=new CustomersResponseDto();
//        customersResponseDto.setCustomerId(updatedCustomer.getCustomerId());
//        customersResponseDto.setFullName(updatedCustomer.getFullName());
//        customersResponseDto.setEmailAddress(updatedCustomer.getEmailAddress());
//        return customersResponseDto;
    }

  
    public String deleteCustomer(int customerId) {
        Customers existingCustomer=customersRepository.findById(customerId).orElseThrow(()-> new CustomerIdNotFoundException("Customer not found with id:"+customerId));
        customersRepository.delete(existingCustomer);
        return "Account Removed successfully";

    }
   
    public CustomersResponseDto  getCustomerByEmail(String customerEmail) {
       Customers customers= customersRepository.findByEmailAddress(customerEmail)
                .orElseThrow(() -> new CustomerEmailNotFoundException(
                        "Customer not found with email: " + customerEmail));
     return convertToResponseDto(customers);
//       CustomersResponseDto customersResponseDto=new CustomersResponseDto();
//       customersResponseDto.setCustomerId(customers.getCustomerId());
//       customersResponseDto.setFullName(customers.getFullName());
//       customersResponseDto.setEmailAddress(customers.getEmailAddress());
//       return customersResponseDto;
    }

    public List<Orders> getCustomerOrders(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId));
        return existingCustomer.getOrders();
    }


    public List<Shipments> getCustomerShipments(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId)); 
        return existingCustomer.getShipments();
    }
    

    private CustomersResponseDto convertToResponseDto(Customers customers) {
    	CustomersResponseDto customersResponseDto=new CustomersResponseDto();
    	customersResponseDto.setCustomerId(customers.getCustomerId());
    	customersResponseDto.setEmailAddress(customers.getEmailAddress());
    	customersResponseDto.setFullName(customers.getFullName());
    	return customersResponseDto;
    }


	
    
}