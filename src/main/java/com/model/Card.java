package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Card {

	@Id
	@GeneratedValue
	private int cardId;
	@ManyToOne
	private UserData userId;
	private String cardName;
	private String cardNo;
	private String exp;
	private String cvv;
	public Card(UserData userId, String cardName, String cardNo, String exp, String cvv) {
		super();
		this.userId = userId;
		this.cardName = cardName;
		this.cardNo = cardNo;
		this.exp = exp;
		this.cvv = cvv;
	}
	
	
}
