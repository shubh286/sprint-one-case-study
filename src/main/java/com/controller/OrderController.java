package com.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appexception.OrderNotFoundException;
import com.model.OrderData;
import com.service.OrderDataService;


@RestController
public class OrderController {

	@Autowired
	OrderDataService service;
	@GetMapping("/getorderbyid/{id}")
    public OrderData getOrderById(@PathVariable int id) throws OrderNotFoundException
    {
        try
        {
            return service.getOrderById(id);
        }
        catch(NoSuchElementException e)
        {
            throw new OrderNotFoundException();
        }
        
    }
	@PatchMapping("/updateorder")
	public ResponseEntity<String> updateOrders(@RequestBody OrderData od)
	{
		service.updateOrder(od);
		return new ResponseEntity<String>("Order Updated",HttpStatus.OK);
	}
	@PostMapping("/placeorder/{cartId}")
    public ResponseEntity<String> placeOrder(@PathVariable int cartId) throws OrderNotFoundException
    {
        try
        {
            OrderData o=new OrderData();
            o.setOrderDate(new java.util.Date());
            service.addOrder(o);
            service.addCartToOrder(o.getOrderId(),cartId);
            return new ResponseEntity<String>("Cart added to order",HttpStatus.OK);
        }
        catch(NoSuchElementException e)
        {
            throw new OrderNotFoundException();
        }
    }
	
}
