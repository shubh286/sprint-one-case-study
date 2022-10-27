package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.PaymentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Cart;
import com.model.OrderData;
import com.model.Payment;
import com.model.UserData;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.UserDataService;

@SpringBootTest
class PaymentTest {

	@Autowired
	UserDataService uservice;
	@Autowired
	CartService cservice;
	@Autowired
	PaymentDAO pdao;
	@Autowired
	OrderDataService oservice;
	@Test
    void testGetPaymentTemplate() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getpayment";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	@Test
	void testGetPaymentService()
	{
		UserData u=new UserData("John","john@gmail.com","Delhi","Goa");
		uservice.addUser(u);
		Cart c=cservice.findCartOfUser(u);
		OrderData o=new OrderData(new java.util.Date(), 40f, "Rec",c);
		oservice.addOrder(o);
		Payment act=pdao.findByOrderId(o);
		int pid=act.getPaymentId();
		Payment exp=pdao.getById(pid);
		assertEquals(exp.toString(), act.toString());
	}
}
