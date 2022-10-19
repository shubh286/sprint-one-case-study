package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class OrderData {

	@Id
	@GeneratedValue
	private int orderId;
	private String orderDate;
	private float totalAmount;
	private String OrderStatus;
	private Cart cartId;
}
