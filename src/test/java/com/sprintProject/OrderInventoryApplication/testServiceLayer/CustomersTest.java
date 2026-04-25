package com.sprintProject.OrderInventoryApplication.testServiceLayer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailAlreadyExistException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerEmailNotFoundException;
import com.sprintProject.OrderInventoryApplication.CustomExceptions.CustomerIdNotFoundException;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Customers;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
import com.sprintProject.OrderInventoryApplication.EntityClasses.Shipments;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.CustomersRepository;
import com.sprintProject.OrderInventoryApplication.RepositoryLayer.ShipmentsRepository;
import com.sprintProject.OrderInventoryApplication.ServiceLayer.CustomersService;
import com.sprintProject.OrderInventoryApplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.CustomersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.OrdersResponseDto;
import com.sprintProject.OrderInventoryApplication.dto.responseDto.ShipmentsResponseDto;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomersTest {
	    @Mock
	    private CustomersRepository customersRepository;

	    @InjectMocks
	    private CustomersService customersService;

	    // 1. CREATE SUCCESS
	    @Test
	    void testCreateCustomerSuccess() {
	        CustomersRequestDto dto = new CustomersRequestDto();
	        dto.setFullName("Abi");
	        dto.setEmailAddress("abi@gmail.com");

	        when(customersRepository.existsByEmailAddress("abi@gmail.com")).thenReturn(false);

	        Customers customer = new Customers();
	        customer.setCustomerId(1);
	        customer.setFullName("Abi");
	        customer.setEmailAddress("abi@gmail.com");

	        when(customersRepository.save(any(Customers.class))).thenReturn(customer);

	        CustomersResponseDto response = customersService.createCustomer(dto);

	        assertNotNull(response);
	        assertEquals("Abi", response.getFullName());
	    }

	    // 2. CREATE DUPLICATE EMAIL
	    @Test
	    void testCreateCustomerDuplicateEmail() {
	        CustomersRequestDto dto = new CustomersRequestDto();
	        dto.setEmailAddress("abi@gmail.com");

	        when(customersRepository.existsByEmailAddress("abi@gmail.com")).thenReturn(true);

	        assertThrows(CustomerEmailAlreadyExistException.class, () -> {
	            customersService.createCustomer(dto);
	        });
	    }

	    // 3. GET ALL CUSTOMERS SUCCESS
	    @Test
	    void testGetAllCustomers() {
	        Customers c1 = new Customers();
	        c1.setCustomerId(1);

	        when(customersRepository.findAll()).thenReturn(List.of(c1));

	        List<CustomersResponseDto> list = customersService.getAllCustomers();

	        assertFalse(list.isEmpty());
	    }

	    // 4. GET ALL CUSTOMERS EMPTY
	    @Test
	    void testGetAllCustomersEmpty() {
	        when(customersRepository.findAll()).thenReturn(new ArrayList<>());

	        List<CustomersResponseDto> list = customersService.getAllCustomers();

	        assertTrue(list.isEmpty());
	    }

	    // 5. GET BY ID SUCCESS
	    @Test
	    void testGetCustomerByIdSuccess() {
	        Customers customer = new Customers();
	        customer.setCustomerId(1);

	        when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

	        CustomersResponseDto response = customersService.getCustomerById(1);

	        assertNotNull(response);
	    }

	    // 6. GET BY ID NOT FOUND
	    @Test
	    void testGetCustomerByIdNotFound() {
	        when(customersRepository.findById(1)).thenReturn(Optional.empty());

	        assertThrows(CustomerIdNotFoundException.class, () -> {
	            customersService.getCustomerById(1);
	        });
	    }

	    // 7. GET BY EMAIL SUCCESS
	    @Test
	    void testGetCustomerByEmailSuccess() {
	        Customers customer = new Customers();
	        customer.setEmailAddress("abi@gmail.com");

	        when(customersRepository.findByEmailAddress("abi@gmail.com"))
	                .thenReturn(Optional.of(customer));

	        CustomersResponseDto response = customersService.getCustomerByEmail("abi@gmail.com");

	        assertNotNull(response);
	    }

	    // 8. GET BY EMAIL NOT FOUND
	    @Test
	    void testGetCustomerByEmailNotFound() {
	        when(customersRepository.findByEmailAddress("abi@gmail.com"))
	                .thenReturn(Optional.empty());

	        assertThrows(CustomerEmailNotFoundException.class, () -> {
	            customersService.getCustomerByEmail("abi@gmail.com");
	        });
	    }

	    // 9. UPDATE SUCCESS
	    @Test
	    void testUpdateCustomerSuccess() {
	        Customers existing = new Customers();
	        existing.setCustomerId(1);
	        existing.setEmailAddress("old@gmail.com");

	        CustomersRequestDto dto = new CustomersRequestDto();
	        dto.setFullName("New");
	        dto.setEmailAddress("new@gmail.com");

	        when(customersRepository.findById(1)).thenReturn(Optional.of(existing));
	        when(customersRepository.existsByEmailAddress("new@gmail.com")).thenReturn(false);
	        when(customersRepository.save(any())).thenReturn(existing);

	        CustomersResponseDto response = customersService.updateCustomer(1, dto);

	        assertEquals("New", response.getFullName());
	    }

	    // 10. UPDATE ID NOT FOUND
	    @Test
	    void testUpdateCustomerIdNotFound() {
	        when(customersRepository.findById(1)).thenReturn(Optional.empty());

	        assertThrows(CustomerIdNotFoundException.class, () -> {
	            customersService.updateCustomer(1, new CustomersRequestDto());
	        });
	    }

	    // 11. UPDATE DUPLICATE EMAIL
	    @Test
	    void testUpdateCustomerDuplicateEmail() {
	        Customers existing = new Customers();
	        existing.setEmailAddress("old@gmail.com");

	        CustomersRequestDto dto = new CustomersRequestDto();
	        dto.setEmailAddress("new@gmail.com");

	        when(customersRepository.findById(1)).thenReturn(Optional.of(existing));
	        when(customersRepository.existsByEmailAddress("new@gmail.com")).thenReturn(true);

	        assertThrows(CustomerEmailAlreadyExistException.class, () -> {
	            customersService.updateCustomer(1, dto);
	        });
	    }

	    // 12. DELETE SUCCESS
	    @Test
	    void testDeleteCustomerSuccess() {
	        Customers customer = new Customers();
	        when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
	        CustomersResponseDto result = customersService.deleteCustomer(1);
	        assertEquals("Account Removed successfully", result);
	    }

	    // 13. DELETE NOT FOUND
	    @Test
	    void testDeleteCustomerNotFound() {
	        when(customersRepository.findById(1)).thenReturn(Optional.empty());
	        
	        assertThrows(CustomerIdNotFoundException.class, () -> {
	            customersService.deleteCustomer(1);
	        });
	    }

	    // 14. GET ORDERS SUCCESS
	    @Test
	    void testGetCustomerOrdersSuccess() {
	        Customers customer = new Customers();
	        customer.setOrders(List.of(new Orders()));
	        
	        when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
	        List<OrdersResponseDto> orders = customersService.getCustomerOrders(1);
	        assertNotNull(orders);
	    }

	    // 15. GET ORDERS NOT FOUND
	    @Test
	    void testGetCustomerOrdersNotFound() {
	        when(customersRepository.findById(1)).thenReturn(Optional.empty());
	        assertThrows(CustomerIdNotFoundException.class, () -> {
	            customersService.getCustomerOrders(1);
	        });
	    }
	    // 16.GET SHIPMENTS SUCCESS
	    @Test
	    void testGetCustomerShipmentsSuccess() {
	        Customers customer = new Customers();
	        List<ShipmentsResponseDto> shipmentList = new ArrayList<>();
	        
	        customer.setShipments(List.of(new Shipments()));
	        
	        when(customersRepository.findById(1)).thenReturn(Optional.of(customer));
	        List<ShipmentsResponseDto> shipments = customersService.getCustomerShipments(1);
	       
	        assertNotNull(shipments);
	        assertFalse(shipments.isEmpty());
	    }
	  
	    //17. SHIPPMENTS NOT FOUND
	    @Test
	    void testGetCustomerShipmentsNotFound() {

	        
	        when(customersRepository.findById(1)).thenReturn(Optional.empty());

	        assertThrows(CustomerIdNotFoundException.class, () -> {
	            customersService.getCustomerShipments(1);
	        });
	    }
	    

}