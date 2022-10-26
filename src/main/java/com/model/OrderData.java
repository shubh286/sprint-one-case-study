package com.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderData {

	@Id
	@GeneratedValue
	private int orderId;
	private Date orderDate;
	private float totalAmount;
	private String OrderStatus;
	@OneToOne
	private Cart cartId;
	public OrderData(Date orderDate, float totalAmount, String orderStatus, Cart cartId) {
		super();
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		OrderStatus = orderStatus;
		this.cartId = cartId;
	}
	
	
}
