package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.OrderData;
import com.model.UserData;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.UserDataService;

@SpringBootTest
class OrderDataTest {

	@Autowired
	UserDataService uservice;
	@Autowired
	OrderDataService oservice;
	@Autowired
	CartService cservice;
	@Test
    void testGetOrdersTemplate() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getorders";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	@Test
    void testAddOrderTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"received",cservice.findCartOfUser(u));
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/addorders";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<OrderData> request = new HttpEntity<>(o,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testUpdateOrderTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"received",cservice.findCartOfUser(u));
		RestTemplate template=new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		final String url="http://localhost:8900/updateorder";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<OrderData> request = new HttpEntity<>(o,headers);
		ResponseEntity<String> res= template.exchange(uri,
                HttpMethod.PATCH,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
}
