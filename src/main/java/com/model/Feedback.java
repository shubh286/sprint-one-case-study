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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {

	@Id
	@GeneratedValue
	private int feedbackId;
	private int userId;
	private int productId;
	private String msgByUser;
	private int rating;
	public Feedback(int userId, int productId, String msgByUser, int rating) {
		super();
		this.userId = userId;
		this.productId = productId;
		this.msgByUser = msgByUser;
		this.rating = rating;
	}
}
