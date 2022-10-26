package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.CartDAO;
import com.dao.ProductDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Cart;
import com.model.Product;
import com.model.UserData;
import com.service.UserDataService;
@SpringBootTest
class CartTest {

	@Autowired
	UserDataService uservice;
	@Autowired
	ProductDAO pdao;
	@Autowired
	CartDAO cdao;
	@Test
	void testAddCart(){
		Cart c=new Cart();
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		c.setUserId(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pdao.save(p);
		List<Product> list=Arrays.asList(p);
		c.setProductList(list);
		Cart actual=cdao.save(c);
		c.setCartId(actual.getCartId());
		u.setUserId(c.getUserId().getUserId());
		p.setProductId(p.getProductId());
		assertEquals(c.toString(), actual.toString());
	}
	@Test
	void testDeleteCart()
	{
		Cart c=new Cart();
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		c.setUserId(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pdao.save(p);
		List<Product> list=Arrays.asList(p);
		c.setProductList(list);
		cdao.save(c);
		cdao.delete(c);
		int actual=0;
		int expected=cdao.countByCartId(c.getCartId());
		assertEquals(actual,expected);
	}
	@Test
	void testGetCart()
	{
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		Cart c=cdao.findByUserId(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pdao.save(p);
		p.setProductId(p.getProductId());
		List<Product> list=Arrays.asList(p);
		c.setProductList(list);
		cdao.save(c);
		Cart cart=cdao.findById(c.getCartId()).get();
		assertEquals(c.toString(), cart.toString());
	}
	@Test
	void testFindCartOfUser()
	{
		Cart c=new Cart();
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		c.setUserId(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pdao.save(p);
		List<Product> list=Arrays.asList(p);
		c.setProductList(list);
		cdao.save(c);
		Cart actual=cdao.findByUserId(u);
		assertEquals(actual.toString(),c.toString());
	}
	@Test
    void testGetCartTemplate() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getcarts";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testGetCartForUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/getcartforuser";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	
}
