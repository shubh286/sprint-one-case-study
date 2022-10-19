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
@NoArgsConstructor
@ToString
@Entity

public class UserData {

	@Id
	@GeneratedValue
	private int userId;
	private String userName;
	private String emailId;
	private String billingAddress;
	private String shippingAddress;
	private boolean loginStatus;
	
}
