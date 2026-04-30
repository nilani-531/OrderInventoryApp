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
import com.sprintProject.orderinventoryapplication.repository.CustomersRepository; // Add this
import com.sprintProject.orderinventoryapplication.repository.StoresRepository; // Add this

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
		// 1. Create and Save Parent Objects (Ensures Foreign Keys exist)
		Customers c = new Customers();
		c.setFullName("Test User");
		c.setEmailAddress("test@capgemini.com");
		Customers savedCustomer = customerRepo.save(c);

		Stores s = new Stores();
		s.setStoreName("Test Store");
		Stores savedStore = storeRepo.save(s);

		// 2. CREATE Order using the SAVED objects
		Orders order = new Orders();

		order.setCustomers(savedCustomer);
		order.setStores(savedStore);

		order.setOrderStatus(OrderStatus.OPEN);
		order.setOrderTms(LocalDateTime.now());

		// 3. SAVE Order
		Orders savedOrder = orderRepo.save(order);

		// 4. VERIFY
		assertNotNull(savedOrder);
		assertNotNull(savedOrder.getOrderId());
		assertEquals(OrderStatus.OPEN, savedOrder.getOrderStatus());

		System.out.println("Green Bar Expected! Saved Order ID: " + savedOrder.getOrderId());
	}
}

