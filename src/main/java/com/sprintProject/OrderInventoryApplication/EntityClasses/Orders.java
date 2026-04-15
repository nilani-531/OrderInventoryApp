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
    private int order_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Stores  store_id;

	@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus order_status;

    @Column(name = "order_tms")
    private LocalDateTime order_tms;

    
    @PrePersist
	public void setDefaultOrderStatus() {
	    if ( order_status == null) {
	    	order_status = OrderStatus.OPEN;
	    }
	}
    
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public Customers getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Customers customer_id) {
		this.customer_id = customer_id;
	}

	public Stores getStore_id() {
		return store_id;
	}

	public void setStore_id(Stores store_id) {
		this.store_id = store_id;
	}

	public OrderStatus getStatus() {
		return order_status;
	}

	public void setStatus(OrderStatus order_status) {
		this.order_status = order_status;
	}

	public LocalDateTime getOrderTime() {
		return order_tms;
	}

	public void setOrderTime(LocalDateTime order_tms) {
		this.order_tms = order_tms;
	}

	
}