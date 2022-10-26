package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ProductDAO;
import com.model.Product;

@Service
public class ProductService {

	@Autowired
	ProductDAO dao;
	
	public List<Product> getAllProduct()
	{
		return dao.findAll();
	}
	public void addProduct(Product p)
	{
		dao.save(p);
	}
	public void updateProduct(Product p)
	{
		dao.save(p);
	}
	public void deleteProduct(int id)
	{
		dao.deleteById(id);
	}
	
}
