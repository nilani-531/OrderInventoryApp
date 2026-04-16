package com.sprintProject.OrderInventoryApplication.EntityClasses;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;



@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customers;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Stores  stores;

	@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatusS;

    @Column(name = "order_tms")
    private LocalDateTime orderTms;

    
    @PrePersist
	public void setDefaultOrderStatus() {
	    if ( orderStatusS == null) {
	    	orderStatusS = OrderStatus.OPEN;
	    }
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public Customers getCustomers() {
		return customers;
	}


	public void setCustomers(Customers customers) {
		this.customers = customers;
	}


	public Stores getStores() {
		return stores;
	}


	public void setStores(Stores stores) {
		this.stores = stores;
	}


	public OrderStatus getOrderStatus() {
		return orderStatusS;
	}


	public void setOrderStatus(OrderStatus orderStatusS) {
		this.orderStatusS = orderStatusS;
	}


	public LocalDateTime getOrderTms() {
		return orderTms;
	}


	public void setOrderTms(LocalDateTime orderTms) {
		this.orderTms = orderTms;
	}
  

	
}