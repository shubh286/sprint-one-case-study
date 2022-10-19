package com.model;

import java.util.List;

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
public class Admin {

	@Id
	@GeneratedValue
	private int adminId;
	private String adminName;
	private String adminPassword;
	private List<UserData> userList;
	private List<Product> productList;
	private OrderData orderId;
}
