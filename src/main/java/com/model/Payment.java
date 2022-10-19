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
public class Payment {

	@Id
	@GeneratedValue
	private int paymentId;
	private OrderData orderId;
	private UserData userId;
	private float amount;
	private String paymentDate;
	private String paymentMode;
}
