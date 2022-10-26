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
public class Feedback {

	@Id
	@GeneratedValue
	private int feedbackId;
	//private UserData userId;
	//private Product productId;
	private String msgByUser;
	private int rating;
}
