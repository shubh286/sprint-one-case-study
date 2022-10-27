package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.appexception.OrderNotFoundException;
import com.controller.OrderController;
import com.dao.CartDAO;
import com.dao.OrderDataDAO;
import com.dao.ProductDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Cart;
import com.model.OrderData;
import com.model.Product;
import com.model.UserData;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.ProductService;
import com.service.UserDataService;

@SpringBootTest
class OrderDataTest {

	@Autowired
	OrderController ocontroller;
	@Autowired
	UserDataService uservice;
	@Autowired
	OrderDataService oservice;
	@Autowired
	CartService cservice;
	@Autowired
	ProductService pservice;
	@Autowired
	OrderDataDAO odao;
	@Autowired
	CartDAO cdao;
	@Autowired
	ProductDAO pdao;
	Cart c;
	OrderData o;
	@BeforeEach
    void init() {
        c=new Cart();    
        cdao.save(c);
        o=new OrderData(new java.util.Date(),23f,"Pending",c);

   }
	@Test
    void testUpdateOrderTemplate() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
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
	
	@Test
    void addCartToOrder()
    {
        Cart c=new Cart();
        cservice.addCart(c);
        int cid=c.getCartId();
        
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<String>(headers);
        
        RestTemplate template=new RestTemplate();
        assertEquals(HttpStatus.OK,template.exchange("http://localhost:8900/placeorder/"+cid, HttpMethod.POST,entity,String.class).getStatusCode());
    }
	/*@Test
    void addCartToOrderService()
    {
		UserData u=new UserData("john","1234","john@gmail.com","Delhi","Goa",true);
		uservice.addUser(u);
		Product p=new Product("coffee",40.5f,10,"Edibles",10f);
		pservice.addProduct(p);
		Cart c=cservice.findCartOfUser(u);
		OrderData o=new OrderData();
		o.setCartId(c);
        oservice.addOrder(o);
        int oid=o.getOrderId();
        int cid=c.getCartId();
        oservice.addCartToOrder(oid, cid);
        OrderData o1=odao.findById(oid).get();
        System.out.println(o1.getCartId().toString());
        assertEquals(o1.getCartId().toString(),c.toString());
        
    }
	@Test
    void addCartToOrderService()
    {
        Cart c=new Cart();
        cservice.addCart(c);
        
        Product p=new Product();
        p.setProductName("Sugar");
        p.setPrice(150);
        p.setCategory("pqr");
        p.setDiscount(5);
        p.setQuantity(10);
        pservice.addProduct(p);
        
        int cid=c.getCartId();
        int pid=p.getProductId();
        
        cservice.addProductToCart(cid, pid);
        
       OrderData o=new OrderData();
        oservice.addOrder(o);
        int oid=o.getOrderId();
        oservice.addCartToOrder(oid, cid);
        
        Cart c1=cdao.findById(cid).get();
        OrderData o1=oservice.getOrderById(oid);
        
        assertEquals(o1.getCartId().toString(),c1.toString());
        
    }*/
	
	@Test
    void testGetById()
    {
        OrderData o=new OrderData();
        oservice.addOrder(o);
        int id=o.getOrderId();
        
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<String>(headers);
        
        RestTemplate template=new RestTemplate();
        assertEquals(HttpStatus.OK,template.exchange("http://localhost:8900/getorderbyid/"+id, HttpMethod.GET,entity,String.class).getStatusCode());
    }
	@Test
    void testUpdateOrder(){
        oservice.addOrder(o);
        OrderData order2=new OrderData(1,new java.util.Date(),13f,"Confirmed",c);
        oservice.updateOrder(order2);
        assertNotNull(odao.findById(order2.getOrderId()));
   }
	@Test
    void testUpdateOrderController() throws Exception
    {
        OrderData o=new OrderData();
        Cart c=new Cart();
        Product p=new Product();
        p.setProductName("Sugar");
        p.setPrice(150);
        p.setCategory("pqr");
        p.setDiscount(5);
        p.setQuantity(10);
        pdao.save(p);
        List<Product> pList=new ArrayList<>();
        pList.add(p);
        c.setProductList(pList);
        
        cservice.addCart(c);
        o.setCartId(c);
        o.setOrderStatus("Ordered");
        o.setTotalAmount(20f);
        
        oservice.addOrder(o);
        
        OrderData o1=new OrderData();
        o1.setOrderId(o.getOrderId());
        o1.setOrderStatus("Recieved");
        
        ocontroller.updateOrders(o1);
        
        assertNotNull(odao.findById(o1.getOrderId()));
    }
	@Test
    public void testGetOrderByIdController() throws OrderNotFoundException
    {
        OrderData o=new OrderData();
        oservice.addOrder(o);
        int id=o.getOrderId();
        
        OrderData o1=ocontroller.getOrderById(id);
        assertEquals(o.getOrderId(),o1.getOrderId());
    }
	@Test
    public void testGetOrderByIdFail()
    {
        Exception exp=assertThrows(OrderNotFoundException.class,()->{
            ocontroller.getOrderById(100);
        });
        
        String expMsg="Order Not Found";
        
        String actual=exp.toString();
        
        assertEquals(expMsg,actual);
    }
/*	@Test
	void testPlaceOrderController() throws Exception
	{
		Cart c=new Cart();
		cdao.save(c);
		ResponseEntity<String> res=ocontroller.placeOrder(c.getCartId());
		assertEquals("Cart added to order", res.getBody());
	}*/
}
