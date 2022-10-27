package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appexception.EmptyCartException;
import com.model.Cart;
import com.model.UserData;
import com.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService service;
	@PostMapping("/getcartforuser")
	public Cart getCartForUser(@RequestBody UserData userId) throws Exception
	{
		try {
		return service.findCartOfUser(userId);
	}
		catch(Exception e)
		{
			throw new EmptyCartException();
		}
	}
/*	@GetMapping("/getcarts")
	public List<Cart> getAllCarts() throws Exception
	{
		try {
		return service.getCart();
	}
		catch(Exception e)
		{
			throw new EmptyCartException();
		}
	}*/
	
}
