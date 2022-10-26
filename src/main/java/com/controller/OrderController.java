package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.Cart;
import com.model.OrderData;
import com.service.OrderDataService;


@RestController
public class OrderController {

	@Autowired
	OrderDataService service;
	
	@GetMapping("/getorders")
	public List<OrderData> getAllOrders()
	{
		return service.getAllOrders();
	}
	@PostMapping("/addorders")
	public ResponseEntity<String> addOrders(@RequestBody OrderData od)
	{
		Cart c=od.getCartId();
		od.setCartId(c);
		service.addOrder(od);
		return new ResponseEntity<String>("Order Added",HttpStatus.OK);
	}
	@PatchMapping("/updateorder")
	public ResponseEntity<String> updateOrders(@RequestBody OrderData od)
	{
		service.updateOrder(od);
		return new ResponseEntity<String>("Order Updated",HttpStatus.OK);
	}
}
