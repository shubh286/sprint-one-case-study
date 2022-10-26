package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

import com.dao.CardDAO;
import com.dao.PaymentDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Card;
import com.model.Cart;
import com.model.OrderData;
import com.model.Payment;
import com.model.Product;
import com.model.UserData;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.ProductService;
import com.service.UserDataService;

@SpringBootTest
class UserDataTests {

	@Autowired
	UserDataService uservice;
	@Autowired
	CartService cservice;
	@Autowired
	ProductService pservice;
	@Autowired
	PaymentDAO pdao;
	@Autowired
	OrderDataService oservice;
	@Autowired
	CardDAO cdao;
	
	@Test
    void testGetUser() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getusers";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testAddUser() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/adduser";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testUpdateUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		RestTemplate template=new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		final String url="http://localhost:8900/updateuser";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res= template.exchange(uri,
                HttpMethod.PATCH,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
	void testdeleteUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/deleteuser";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<UserData> request = new HttpEntity<>(u, headers);
		ResponseEntity<String>  res;
	    res=template.exchange(uri, HttpMethod.DELETE, request, String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testGetProductByUserTemplate() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getproductbyuser";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testAddProductToCartByUser() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pservice.addProduct(p);
		Cart c=cservice.findCartOfUser(u);
		int cid=c.getCartId();
		int pid=p.getProductId();
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/addproducttocartbyuser/"+pid+"/"+cid+"";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testDeleteProductToCartByUser() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pservice.addProduct(p);
		Cart c=cservice.findCartOfUser(u);
		int cid=c.getCartId();
		int pid=p.getProductId();
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/addproducttocartbyuser/"+pid+"/"+cid+"";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testUpdatePaymentByUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"Received",cservice.findCartOfUser(u));
		oservice.addOrder(o);
		Payment p=pdao.findByOrderId(o);
		int pid=p.getPaymentId();
		int oid=o.getOrderId();
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/updatepayment/"+pid+"/"+oid+"/Card";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testAddCardByUserTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"Received",cservice.findCartOfUser(u));
		oservice.addOrder(o);
		Payment p=pdao.findByOrderId(o);
		int pid=p.getPaymentId();
		int uid=u.getUserId();
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/addcard/"+pid+"/"+uid+"";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();      
		HttpEntity<UserData> request = new HttpEntity<>(u,headers);
		ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testViewCardTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"Received",cservice.findCartOfUser(u));
		oservice.addOrder(o);
		Payment p=pdao.findByOrderId(o);
		int pid=p.getPaymentId();
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/viewcards/"+pid+"";
		URI uri=new URI(url);
		ResponseEntity<String> res=template.getForEntity(uri,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testSelectCardTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		OrderData o=new OrderData(new java.util.Date(),400f,"Received",cservice.findCartOfUser(u));
		oservice.addOrder(o);
		Card x=new Card(u,"John","543323445","21/21","456");
		cdao.save(x);
		Payment p=pdao.findByOrderId(o);
		int pid=p.getPaymentId();
		List<Card> cards=p.getCardDetails();
		int cid = 0;
		for(Card c:cards)
		{
			if(c.getCardId()==x.getCardId())
				cid=c.getCardId();
		}
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/selectcard/"+pid+"/"+cid+"";
		URI uri=new URI(url);
		ResponseEntity<String> res=template.getForEntity(uri,String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	@Test
    void testGetPaymentForUser() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getpaymentforuser";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	
	
	 Payment payment=new Payment(1,null,5000,new Date(),null,null);
	    Payment payment1=new Payment(2,null,3000,new Date(),null,null);



	   
	    @Test
	    void updatePaymentModeTest() {
	        String exp=payment.getPaymentMode();
	        service.updatePaymentMode(payment, "Cash-on-Delivery");
	        String actual=payment.getPaymentMode();
	        Assertions.assertNotNull(payment.getPaymentMode());
	        Assertions.assertNotEquals(exp, actual);
	    }
	    
	    @Test
	    void settingPaymentModeTest() {
	        System.out.println(new Date());
	        pdao.save(payment);
	        int id=pdao.findById(payment.getPaymentId()).get().getPaymentId();
	        String exp=pdao.findById(payment.getPaymentId()).get().getPaymentMode();
	        service.settingPaymentMode(id, "Cash-on-Delivery");
	        String actual=pdao.findById(payment.getPaymentId()).get().getPaymentMode();
	        //System.out.println(id+" "+exp+" "+actual);
	        Assertions.assertNotNull(actual);
	        Assertions.assertNotEquals(exp, actual);
	    }
	    
//	    @Test
//	    void testAddPaymentObject(){
//	        service.addPaymentObject(payment);
//	        Payment p=pdao.findById(1).get();
//	        Assertions.assertEquals(payment.toString(),p.toString());
//	    }
	    
	    @Test
	    void testaddCardToPayment() {
	        Card c1=new Card("987456321","Peter","09/24","456",null);
	        pdao.save(payment);
	        cdao.save(c1);
	        List<Card> expList=pdao.findById(1).get().getCardList();
	        service.addCardtoPayment(1, c1);
	        List<Card> actualList=pdao.findById(1).get().getCardList();
	        System.out.println(pdao.findById(1).get().getCardList().get(0).toString());
	        System.out.println(expList+" "+actualList);
	        Assertions.assertNotNull(actualList);
	        Assertions.assertNotEquals(expList, actualList);
	    }
	    
	    @Test
	    void testGetCardListById() {
	        pdao.save(payment);
	        Assertions.assertEquals(0,service.getCardListById(1).size());
	    }
	    
	    @Test
	    void testSelectCardByCardId() {
	        Card c1=new Card("987456321","Peter","09/24","456",null);
	        pdao.save(payment);
	        cdao.save(c1);
	        service.addCardtoPayment(1, c1);
	        Card expcard=pdao.findById(1).get().getCardList().get(0);
	        Card actual=service.selectCardByCardId(1, c1.getCardId());
	        Assertions.assertEquals(expcard.toString(), actual.toString());
	    }

*/

	}

