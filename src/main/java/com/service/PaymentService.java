package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.model.Payment;

@Service
public class PaymentService   {

	@Autowired
	PaymentDAO dao;
	@Autowired
	OrderDataDAO odao;
	public List<Payment> getPayment()
	{
		return dao.findAll();
	}
}
