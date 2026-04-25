package com.sprintProject.OrderInventoryApplication.ServiceLayer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailAlreadyExistException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerIdNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.OrderNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.ShipmentNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ShipmentsRepository;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;

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
    public CustomersResponseDto deleteCustomer(int customerId) {

        Customers existingCustomer = customersRepository.findById(customerId)
            .orElseThrow(() -> new CustomerIdNotFoundException(
                "Customer not found with id: " + customerId));

        // Convert BEFORE deleting
        CustomersResponseDto deletedCustomer = convertToResponseDto(existingCustomer);

        customersRepository.delete(existingCustomer);

        return deletedCustomer;
    }
   
    // Get Customer By Email
    public CustomersResponseDto  getCustomerByEmail(String customerEmail) {
       Customers customers= customersRepository.findByEmailAddress(customerEmail)
                .orElseThrow(() -> new CustomerEmailNotFoundException(
                        "Customer not found with email: " + customerEmail));
     return convertToResponseDto(customers);

    }

   // Fetch orders from relationship
    public List<OrdersResponseDto> getCustomerOrders(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId));
        List<Orders> ordersList= existingCustomer.getOrders();
        if (ordersList == null || ordersList.isEmpty()) {
            throw new OrderNotFoundException(
                    "No Orders found for Customer id: " + customerId);
        }
        return ordersList.stream()
                .map(this::convertToOrderDto)
                .toList();
    }

    // Fetch shipments from relationship
    public List<ShipmentsResponseDto> getCustomerShipments(int customerId) {
        Customers existingCustomer =customersRepository.findById(customerId) .orElseThrow(()->new CustomerIdNotFoundException("Customer not found with id:"+customerId)); 
        List<Shipments> shipmentsList= existingCustomer.getShipments();
        if( shipmentsList ==null ||shipmentsList.isEmpty()) {
        	throw new ShipmentNotFoundException("No Shipment found for Customer id: "+customerId);
        }
        return shipmentsList.stream()
                .map(this::convertToShipmentDto)
                .toList();
    }
    
    // ResponseDto conversion
    private CustomersResponseDto convertToResponseDto(Customers customers) {
    	CustomersResponseDto customersResponseDto=new CustomersResponseDto();
    	customersResponseDto.setCustomerId(customers.getCustomerId());
    	customersResponseDto.setEmailAddress(customers.getEmailAddress());
    	customersResponseDto.setFullName(customers.getFullName());
    	return customersResponseDto;
    }
    
    // OrderResponse conversion
    private OrdersResponseDto convertToOrderDto(Orders order) {
        OrdersResponseDto dto = new OrdersResponseDto();

        dto.setOrderId(order.getOrderId());
        dto.setOrderTms(order.getOrderTms());
        dto.setOrderStatusS(order.getOrderStatus());
        if (order.getCustomers() != null) {
            dto.setCustomerId(order.getCustomers().getCustomerId()); 
        }
        if (order.getStores() != null) {
            dto.setStoreId(order.getStores().getStoreId());
            
        }
        return dto;
    }
    
    // ShipmentsResponse Conversion
    private ShipmentsResponseDto convertToShipmentDto(Shipments shipment) {

        ShipmentsResponseDto dto = new ShipmentsResponseDto();

        dto.setShipmentId(shipment.getShipmentId());
        dto.setShipmentStatus(shipment.getShipmentStatus());
        dto.setDeliveryAddress(shipment.getDeliveryAddress());

        if (shipment.getCustomers() != null) {
            dto.setCustomerId(shipment.getCustomers().getCustomerId());
            
        }
        if (shipment.getStores() != null) {
            dto.setStoreId(shipment.getStores().getStoreId());   
        }
        return dto;
    }
}
