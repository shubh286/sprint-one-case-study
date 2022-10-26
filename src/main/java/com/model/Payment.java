package com.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	@OneToOne(cascade = CascadeType.ALL)
	private OrderData orderId;
	private float amount;
	private Date paymentDate;
	private String paymentMode;
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Card> cardDetails;
}
