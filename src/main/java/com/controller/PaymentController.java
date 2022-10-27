package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Payment;
import com.service.PaymentService;

@RestController
public class PaymentController {

	@Autowired
	PaymentService service;
	@GetMapping("/getpayment")
	public List<Payment> getPayment() throws Exception
	{
		return service.getPayment();
	}
}
