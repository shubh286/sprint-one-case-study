package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDAO;
import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.model.OrderData;
import com.model.Payment;

@Service
public class OrderDataService {

	@Autowired
	OrderDataDAO dao;
	@Autowired
	PaymentDAO pdao;
	@Autowired
	CartDAO cdao;
	
	public List<OrderData> getAllOrders()
	{
		return dao.findAll();
	}
	public void addOrder(OrderData od)
	{
		Payment p=new Payment();
		p.setOrderId(od);
		pdao.save(p);
		od.setOrderDate(new java.util.Date());
		dao.save(od);
	}
	public void updateOrder(OrderData od)
	{
		dao.save(od);
	}
	public void deleteOrder(int id)
	{
		dao.deleteById(id);
	}
}