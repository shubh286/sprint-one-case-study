package com.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
public class Cart {

	@Id
	@GeneratedValue
	private int cartId;
	@OneToOne(cascade = CascadeType.ALL)
	private UserData userId;
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Product> productList;
	
	
	public Cart(UserData userId, List<Product> productList) {
		super();
		this.userId = userId;
		this.productList = productList;
	}
	
}
