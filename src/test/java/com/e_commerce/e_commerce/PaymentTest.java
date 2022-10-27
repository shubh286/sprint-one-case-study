package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.controller.PaymentController;
import com.dao.PaymentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Cart;
import com.model.OrderData;
import com.model.Payment;
import com.model.UserData;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.PaymentService;
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
	@Autowired
	PaymentService pservice;
	@Autowired
	PaymentController pcontroller;
	@Test
    void testGetPaymentTemplate() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getpayment";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testGetPaymentService() {
        pdao.deleteAllInBatch();
        UserData u=new UserData("John","john@gmail.com","Delhi","Goa");
        uservice.addUser(u);
        Cart c=cservice.findCartOfUser(u);
        OrderData o=new OrderData(new java.util.Date(), 40f, "Rec",c);
        oservice.addOrder(o);
        Payment actual=pdao.findByOrderId(o);
        List<Payment> expList=new ArrayList<>();
        expList.add(actual);
        List<Payment> actualList=pservice.getPayment();
        //System.out.println(expList.toString()+" "+actualList.toString());
        assertEquals(expList.toString(),actualList.toString());
    }

	@Test
    void testGetPayment() throws Exception
    {
        pdao.deleteAllInBatch();
        UserData u=new UserData("John","john@gmail.com","Delhi","Goa");
        uservice.addUser(u);
        Cart c=cservice.findCartOfUser(u);
        OrderData o=new OrderData(new java.util.Date(), 40f, "Rec",c);
        oservice.addOrder(o);
        Payment actual=pdao.findByOrderId(o);
        List<Payment> expList=new ArrayList<>();
        expList.add(actual);
        List<Payment> actualList=pcontroller.getPayment();
        //System.out.println(expList.toString()+" "+actualList.toString());
        assertEquals(expList.toString(),actualList.toString());
    }
}
