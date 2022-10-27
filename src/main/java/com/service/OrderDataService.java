package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDAO;
import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.model.Cart;
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
	public void deleteOrder(OrderData o)
	{
		dao.delete(o);
	}
	public void addCartToOrder(int orderId,int cartId)
    {
        OrderData o=dao.findById(orderId).get();
		Payment p=new Payment();
		p.setOrderId(o);
		pdao.save(p);
        Cart c=cdao.findById(cartId).get();
        o.setCartId(c);
        o.setOrderStatus("Order Placed");
        dao.save(o);
            
    }
	public String getOrderStatus(int id)
    {
        OrderData o=dao.findById(id).get();
        return o.getOrderStatus();
    }
	public void setOrderStatus(int id,String status)
    {
        OrderData o=dao.findById(id).get();
        o.setOrderStatus(status);
        dao.save(o);
    }
	public OrderData getOrderById(int id)
	{
		return dao.findById(id).get();
	}
	
}