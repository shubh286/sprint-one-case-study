package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appexception.UserNotLoginException;
import com.dao.CardDAO;
import com.dao.OrderDataDAO;
import com.dao.PaymentDAO;
import com.dao.ProductDAO;
import com.dao.UserDataDAO;
import com.model.Card;
import com.model.Cart;
import com.model.OrderData;
import com.model.Payment;
import com.model.Product;
import com.model.UserData;

@Service
public class UserDataService {

	@Autowired
	UserDataDAO dao;
	@Autowired
	ProductDAO pdao;
	@Autowired
	PaymentDAO paydao;
	@Autowired
	OrderDataDAO odao;
	@Autowired
	CardDAO cdao;
	@Autowired
	CartService cartService;
	
	public List<UserData> getAllUser()
	{
		return dao.findAll();
	}
	public void addUser(UserData u)
	{
		Cart c=new Cart();
		c.setUserId(u);
		cartService.addCart(c);
		dao.save(u);
	}
	public void updateUser(UserData u)
	{
		dao.save(u);
	}
	public void deleteUser(UserData u)
	{
		dao.delete(u);
	}
	public List<Product> getProductsByUser()
	{
		return pdao.findAll();
	}
	public void addProductByUserToCart(int pid,int cid)
	{
		cartService.addProductToCart(pid,cid);
	}
	public void removeProductByUserFromCart(int pid,int cid)
	{
		cartService.removeProductFromCart(pid, cid);
	}
	public void updatePayment(int pid,int oid,String paymentMode)
	{
		Payment p=paydao.getById(pid);
		OrderData o=odao.getById(oid);
		float amount=o.getTotalAmount();
		p.setAmount(amount);
		p.setPaymentDate(new java.util.Date());
		p.setPaymentMode(paymentMode);
		paydao.save(p);
	}
	public void addCardDetails(int pid,int uid,Card c)
	{
		UserData u=dao.getById(uid);
		c.setUserId(u);
		cdao.save(c);
		Payment p=paydao.getById(pid);
		List<Card> cards=p.getCardDetails();
		cards.add(c);
		p.setCardDetails(cards);
		paydao.save(p);
	}
	public List<Card> viewCards(int pid)
	{
		Payment p=paydao.getById(pid);
		return p.getCardDetails();
	}
	public boolean selectCard(int pid,int cid)
	{
		Payment p=paydao.getById(pid);
		List<Card> card=p.getCardDetails();
		for(Card c:card)
		{
			if(cid==c.getCardId())
				return true;
		}
		return false;
	}
	public boolean checkLoginStatus(String name) throws Exception
	{
		try {
		UserData u=dao.findByUserName(name);
		if(u.isLoginStatus()==true)
			return true;
		else
			return false;
	}
		catch(NullPointerException e)
		{
			throw new UserNotLoginException();
		}
		catch(Exception e)
		{
			throw new UserNotLoginException();
		}
	}
} 
