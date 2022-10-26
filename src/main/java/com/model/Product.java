package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Product {

	@Id
	@GeneratedValue
	private int productId;
	private String productName;
	private float price;
	private int quantity;
	private String category;
	private float discount;

	public Product(String productName, float price, int quantity, String category, float discount) {
		super();
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.category = category;
		this.discount = discount;
	}
}
