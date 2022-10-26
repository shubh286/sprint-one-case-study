package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Cart;
import com.model.UserData;
import com.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService service;
	@GetMapping("/getcarts")
	public List<Cart> getAllCarts()
	{
		return service.getCart();
	}
	@PostMapping("/getcartforuser")
	public Cart getCartForUser(@RequestBody UserData userId)
	{
		return service.findCartOfUser(userId);
	}
}
