package com.sprintProject.OrderInventoryApplication.ServiceLayer;
	import java.time.LocalDateTime;
	import java.util.List;

	import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
	import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;
    import java.time.LocalDateTime;
		import java.util.List;

		import com.sprintProject.OrderInventoryApplication.EntityClasses.Orders;
		import com.sprintProject.OrderInventoryApplication.EntityClasses.OrderStatus;

		public interface OrdersServiceInterface {

		    Orders createOrder(Orders order);

		    List<Orders> getAllOrders();

		    Orders getOrderById(int orderId);

//		    Orders updateOrder(int orderId, Orders order);

		    void deleteOrder(int orderId);

		  
		    Orders updateOrderStatus(int orderId, OrderStatus status);
		}