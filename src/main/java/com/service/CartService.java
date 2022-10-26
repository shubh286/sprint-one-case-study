package com.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CartDAO;
import com.dao.ProductDAO;
import com.model.Cart;
import com.model.Product;
import com.model.UserData;

@Service
public class CartService {

	@Autowired
	CartDAO dao;
	@Autowired
	ProductDAO pdao;
	
	public List<Cart> getCart()
	{
		return dao.findAll();
	}
	public void addCart(Cart c)
	{
		dao.save(c);
	}
	public void addProductToCart(int pid,int cid)
	{
		Cart c=dao.getById(cid);
		Product p=pdao.getById(pid);
		List<Product> list=c.getProductList();
		list.add(p);
		c.setProductList(list);
		dao.save(c);
	}
	public void removeProductFromCart(int pid,int cid)
	{
		Cart c=dao.getById(cid);
		Product p=pdao.getById(pid);
		List<Product> list=c.getProductList();
		list.remove(p);
		c.setProductList(list);
		dao.save(c);
	}
	public Cart findCartOfUser(UserData userId)
	{
		return dao.findByUserId(userId);
	}
}
