package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.model.OrderData;
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

	public void addPayment(Payment p)
	{
		dao.save(p);
	}
	public void updatePayment(Payment p)
	{
		dao.save(p);
	}
	public void deletePayment(Payment p)
	{
		dao.delete(p);
	}
	public void updatePayment(int pid,int oid,String paymentMode)
	{
		Payment p=dao.getById(pid);
		OrderData o=odao.getById(oid);
		float amount=o.getTotalAmount();
		p.setAmount(amount);
		p.setPaymentDate(new java.util.Date());
		p.setPaymentMode(paymentMode);
		dao.save(p);
	}
}
