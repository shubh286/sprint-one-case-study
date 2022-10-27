package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDAO;
import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.model.Cart;
import com.model.OrderData;
import com.model.Payment;
import com.model.Product;

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
		dao.saveAndFlush(od);
	}
	public void deleteOrder(OrderData o)
	{
		dao.delete(o);
	}
	public void addCartToOrder(int orderId,int cartId)
    {
        Cart c=cdao.findById(cartId).get();
        OrderData o=dao.findById(orderId).get();
        
        o.setCartId(c);
        List<Product> pList=c.getProductList();
        int amount=0;
        for(Product p:pList)
        {
            float discount=p.getDiscount();
            float totalAmount=p.getPrice()*p.getQuantity();
            float afterDiscount=totalAmount-totalAmount*discount/100;
            amount+=afterDiscount;
        }
        o.setTotalAmount(amount);
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