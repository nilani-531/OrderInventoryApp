package com.sprintProject.orderinventoryapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.sprintProject.orderinventoryapplication.entity.Customers;
import com.sprintProject.orderinventoryapplication.entity.OrderStatus;
import com.sprintProject.orderinventoryapplication.entity.Orders;
import com.sprintProject.orderinventoryapplication.entity.Stores;
import com.sprintProject.orderinventoryapplication.repository.OrdersRepository;
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository;
import com.sprintProject.orderinventoryapplication.repository.StoresRepository;

@SpringBootTest
@Transactional
class OrderInventoryApplicationTests {

	@Autowired
	private OrdersRepository orderRepo;

	@Autowired
	private CustomersRepository customerRepo;

	@Autowired
	private StoresRepository storeRepo;

	@Test
	void testOrderCRUD() {

		// 1. Create Customer
		Customers c = new Customers();
		c.setFullName("Test User");
		c.setEmailAddress("test@capgemini.com");
		Customers savedCustomer = customerRepo.save(c);

		// 2. Create Store (✅ FIXED)
		Stores s = new Stores();
		s.setStoreName("Test Store");

		//  IMPORTANT FIX
		s.setPhysicalAddress("Chennai");
		// OR you can use: s.setWebAddress("www.test.com");

		Stores savedStore = storeRepo.save(s);

		// 3. Create Order
		Orders order = new Orders();
		order.setCustomers(savedCustomer);
		order.setStores(savedStore);
		order.setOrderStatus(OrderStatus.OPEN);
		order.setOrderTms(LocalDateTime.now());

		// 4. Save Order
		Orders savedOrder = orderRepo.save(order);

		// 5. Assertions
		assertNotNull(savedOrder);
		assertNotNull(savedOrder.getOrderId());
		assertEquals(OrderStatus.OPEN, savedOrder.getOrderStatus());

		System.out.println("✅ Green Bar! Saved Order ID: " + savedOrder.getOrderId());
	}
}