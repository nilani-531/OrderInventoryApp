package com.sprintProject.orderinventoryapplication.testservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sprintProject.orderinventoryapplication.customexception.*;
import com.sprintProject.orderinventoryapplication.entity.*;
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository;
import com.sprintProject.orderinventoryapplication.service.CustomersService;
import com.sprintProject.orderinventoryapplication.dto.requestDto.CustomersRequestDto;
import com.sprintProject.orderinventoryapplication.dto.responseDto.*;

@ExtendWith(MockitoExtension.class)
class CustomersTest {

	@Mock
	private CustomersRepository customersRepository;

	@InjectMocks
	private CustomersService customersService;

	// =====================================================
	// ✅ POSITIVE TEST CASES (HAPPY PATH)
	// =====================================================

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

	// 2. GET ALL CUSTOMERS SUCCESS
	@Test
	void testGetAllCustomers() {
		Customers c1 = new Customers();
		c1.setCustomerId(1);

		when(customersRepository.findAll()).thenReturn(List.of(c1));

		List<CustomersResponseDto> list = customersService.getAllCustomers();

		assertFalse(list.isEmpty());
	}

	// 3. GET BY ID SUCCESS
	@Test
	void testGetCustomerByIdSuccess() {
		Customers customer = new Customers();
		customer.setCustomerId(1);

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		CustomersResponseDto response = customersService.getCustomerById(1);

		assertNotNull(response);
	}

	// 4. GET BY EMAIL SUCCESS
	@Test
	void testGetCustomerByEmailSuccess() {
		Customers customer = new Customers();
		customer.setEmailAddress("abi@gmail.com");

		when(customersRepository.findByEmailAddress("abi@gmail.com"))
				.thenReturn(Optional.of(customer));

		CustomersResponseDto response = customersService.getCustomerByEmail("abi@gmail.com");

		assertNotNull(response);
	}

	// 5. UPDATE SUCCESS
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

	// 6. DELETE SUCCESS
	@Test
	void testDeleteCustomerSuccess() {
		Customers customer = new Customers();
		customer.setCustomerId(1);

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		CustomersResponseDto result = customersService.deleteCustomer(1);

		assertNotNull(result);
		assertEquals(1, result.getCustomerId());

		verify(customersRepository).delete(customer);
	}

	// 7. GET ORDERS SUCCESS
	@Test
	void testGetCustomerOrdersSuccess() {
		Customers customer = new Customers();
		customer.setOrders(List.of(new Orders()));

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		List<OrdersResponseDto> orders = customersService.getCustomerOrders(1);

		assertNotNull(orders);
		assertFalse(orders.isEmpty());
	}

	// 8. GET SHIPMENTS SUCCESS
	@Test
	void testGetCustomerShipmentsSuccess() {
		Customers customer = new Customers();
		customer.setShipments(List.of(new Shipments()));

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		List<ShipmentsResponseDto> shipments = customersService.getCustomerShipments(1);

		assertNotNull(shipments);
		assertFalse(shipments.isEmpty());
	}

	// =====================================================
	// ❌ NEGATIVE TEST CASES (EXCEPTION / EDGE CASES)
	// =====================================================

	// 9. CREATE DUPLICATE EMAIL
	@Test
	void testCreateCustomerDuplicateEmail() {
		CustomersRequestDto dto = new CustomersRequestDto();
		dto.setEmailAddress("abi@gmail.com");

		when(customersRepository.existsByEmailAddress("abi@gmail.com")).thenReturn(true);

		assertThrows(CustomerEmailAlreadyExistException.class, () -> {
			customersService.createCustomer(dto);
		});
	}

	// 10. GET ALL CUSTOMERS EMPTY (EDGE CASE)
	@Test
	void testGetAllCustomersEmpty() {
		when(customersRepository.findAll()).thenReturn(new ArrayList<>());

		List<CustomersResponseDto> list = customersService.getAllCustomers();

		assertTrue(list.isEmpty());
	}

	// 11. GET BY ID NOT FOUND
	@Test
	void testGetCustomerByIdNotFound() {
		when(customersRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(CustomerIdNotFoundException.class, () -> {
			customersService.getCustomerById(1);
		});
	}

	// 12. GET BY EMAIL NOT FOUND
	@Test
	void testGetCustomerByEmailNotFound() {
		when(customersRepository.findByEmailAddress("abi@gmail.com"))
				.thenReturn(Optional.empty());

		assertThrows(CustomerEmailNotFoundException.class, () -> {
			customersService.getCustomerByEmail("abi@gmail.com");
		});
	}

	// 13. UPDATE ID NOT FOUND
	@Test
	void testUpdateCustomerIdNotFound() {
		when(customersRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(CustomerIdNotFoundException.class, () -> {
			customersService.updateCustomer(1, new CustomersRequestDto());
		});
	}

	// 14. UPDATE DUPLICATE EMAIL
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

	// 15. DELETE NOT FOUND
	@Test
	void testDeleteCustomerNotFound() {
		when(customersRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(CustomerIdNotFoundException.class, () -> {
			customersService.deleteCustomer(1);
		});
	}

	// 16. GET ORDERS EMPTY
	@Test
	void testGetCustomerOrdersEmpty() {
		Customers customer = new Customers();
		customer.setOrders(new ArrayList<>());

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		assertThrows(OrderNotFoundException.class, () -> {
			customersService.getCustomerOrders(1);
		});
	}

	// 17. GET SHIPMENTS EMPTY
	@Test
	void testGetCustomerShipmentsEmpty() {
		Customers customer = new Customers();
		customer.setShipments(new ArrayList<>());

		when(customersRepository.findById(1)).thenReturn(Optional.of(customer));

		assertThrows(ShipmentNotFoundException.class, () -> {
			customersService.getCustomerShipments(1);
		});
	}

	// 18. GET SHIPMENTS CUSTOMER NOT FOUND
	@Test
	void testGetCustomerShipmentsNotFound() {
		when(customersRepository.findById(1)).thenReturn(Optional.empty());

		assertThrows(CustomerIdNotFoundException.class, () -> {
			customersService.getCustomerShipments(1);
		});
	}
}