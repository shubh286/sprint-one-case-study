package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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

import com.appexception.EmptyCartException;
import com.controller.CartController;
import com.dao.CartDAO;
import com.dao.ProductDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Cart;
import com.model.Product;
import com.model.UserData;
import com.service.CartService;
import com.service.UserDataService;
@SpringBootTest
class CartTest {

	@Autowired
	UserDataService uservice;
	@Autowired
	ProductDAO pdao;
	@Autowired
	CartDAO cdao;
	@Autowired
	CartService cserv;
	@Autowired
	CartController cController;
	@Test
	void testAddCart(){
		Cart c=new Cart();
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
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
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
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
		Cart c=new Cart();
		cdao.save(c);
		List<Cart> act=cdao.findAll();
		List<Cart> exp=cserv.getCart();
		assertEquals(act.toString(),exp.toString());
	}
	@Test
	void testFindCartOfUser()
	{
		Cart c=new Cart();
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
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
    void testGetCartForUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/getcartforuser";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
	void testAddProductToCart()
	{
		Product p=new Product();
		pdao.save(p);
		List<Product> plist=new ArrayList<>();
		Cart c=new Cart();
		c.setProductList(plist);
		cdao.save(c);
		plist.add(p);
		c.setProductList(plist);
		cserv.addProductToCart(p.getProductId(), c.getCartId());
		Cart exp=cdao.findById(c.getCartId()).get();
		assertEquals(c.toString(), exp.toString());
	}
	@Test
    void testgetCartForUserController() throws Exception
    {
        Cart c=new Cart();
        UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
        c.setUserId(u);
        Product p=new Product("coffee",40.5f,10,"Edibles",10f);
        pdao.save(p);
        List<Product> list=Arrays.asList(p);
        c.setProductList(list);
        cdao.save(c);
        Cart actual=cController.getCartForUser(u);
        assertEquals(actual.toString(),c.toString());
            
    }
	@Test
    void testgetCartForUserFail()
    {
        Exception exp=assertThrows(EmptyCartException.class,()->{
            Cart c=new Cart();
            UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
            Product p=new Product("coffee",40.5f,10,"Edibles",10f);
            pdao.save(p);
            List<Product> list=Arrays.asList(p);
            c.setProductList(list);
            cdao.save(c);
            Cart actual=cController.getCartForUser(u);
            
        });
        
        String expMsg="Cart Not Found";
        
        String actual=exp.toString();
        
        assertEquals(expMsg,actual);
    }
}
