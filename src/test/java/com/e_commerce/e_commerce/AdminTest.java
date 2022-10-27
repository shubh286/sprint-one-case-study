package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterEach;
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

import com.annotations.ExcludedFromGeneratedCodeCoverage;
import com.appexception.AdminExistsException;
import com.appexception.AdminNotFoundException;
import com.appexception.AdminNotLoggedException;
import com.appexception.EmptyListReturnedException;
import com.appexception.ListEmptyException;
import com.appexception.ObjectAddFailException;
import com.appexception.ProductNotFoundException;
import com.appexception.UserNotFoundException;
import com.controller.AdminController;
import com.dao.AdminDAO;
import com.dao.CartDAO;
import com.dao.OrderDataDAO;
import com.dao.ProductDAO;
import com.dao.UserDataDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Admin;
import com.model.Cart;
import com.model.OrderData;
import com.model.Product;
import com.model.UserData;
import com.service.AdminService;
import com.service.CartService;
import com.service.OrderDataService;
import com.service.UserDataService;
@SpringBootTest
@ExcludedFromGeneratedCodeCoverage
class AdminTest {

	@Autowired
	AdminController adminController;
	@Autowired
	AdminDAO admindao;
	@Autowired
	ProductDAO pdao;
	@Autowired 
	OrderDataDAO orderdao;
	@Autowired
	CartDAO cartdao;
	@Autowired
	UserDataDAO userdao;
	@Autowired
	OrderDataService oservice;
	@Autowired
	AdminService aser;
	@Autowired
	UserDataService userv;
	@Autowired
	CartService cserv;
	Cart cart1;
	OrderData i;
	@BeforeEach
	void init() {
		cart1=new Cart();
		cartdao.save(cart1);
		i=new OrderData(new java.util.Date(),20f,"Pending",cart1);
		orderdao.save(i);
	}
	@AfterEach
	void dest()
	{
		admindao.deleteAll();
        pdao.deleteAll();
	}
	@Test
    void testGetUsersFromAdmin() throws URISyntaxException, JsonProcessingException {
		UserData u=new UserData("John","1234","john@gmail.com","Delhi","Goa",true);
		userdao.save(u);
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getalluserfromadmin";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
      
	}
	@Test
	void testdeleteOrder() throws URISyntaxException, JsonProcessingException {
		RestTemplate template=new RestTemplate();
		final String url="http://localhost:8900/deleteorderbyadmin";
		URI uri=new URI(url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<OrderData> request = new HttpEntity<>(i, headers);
		ResponseEntity<String> res=template.exchange(uri, HttpMethod.DELETE, request, String.class);
		assertEquals(HttpStatus.OK,res.getStatusCode());
	
	}
	@Test
    void testSetOrderStatus()
    {
        OrderData o=new OrderData();
        oservice.addOrder(o);
        int id=o.getOrderId();
        String status="dispatched";
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<String>(headers);
        
        RestTemplate template=new RestTemplate();
        assertEquals(HttpStatus.OK,template.exchange("http://localhost:8900/setorderstatus/"+id+"/"+status, HttpMethod.POST,entity,String.class).getStatusCode());
        
    }
	 @Test
	    void testSetOrderSatusService()
	    {
	        OrderData o=new OrderData();
	        oservice.addOrder(o);
	        
	        oservice.setOrderStatus(o.getOrderId(), "Ordered");
	        OrderData o1=oservice.getOrderById(o.getOrderId());
	        assertEquals(o1.getOrderStatus(),"Ordered");
	    }
	@Test
    void testAddAdminServ() {
        Admin a1=new Admin("admin","admin123");
        Admin actual=admindao.save(a1);
        assertEquals(actual.toString(),a1.toString());
    }
	@Test
    void testGetAllAdminServ() {
        Admin a1=new Admin("admin","admin123");
        admindao.save(a1);
        Admin a2=new Admin("xyz","xyz123");
        admindao.save(a2);
        long actual=2;
        long expected=admindao.count();
        assertEquals(actual, expected);
    }
	@Test
    void testAdminLoginServ() {
        Admin a1=new Admin("admin","admin123");
        admindao.save(a1);
        Admin a2=new Admin("admin","admin123");
        a2.setAdminId(a1.getAdminId());
        assertEquals(a1.toString(),a2.toString());
    }
	@Test
    void testAdminLogoutServ() throws AdminNotFoundException, AdminNotLoggedException{
        Admin a1=new Admin("admin","admin123");
        admindao.save(a1);
        aser.adminLogin(a1);
        aser.adminLogout(a1);
        boolean actual=false;
        boolean expected=admindao.findById(a1.getAdminId()).get().isLoginStatus();
        assertEquals(actual, expected);

    }
	@Test
    void testAddProductsServ() {
        Product p1=new Product("bread",30f,22,"food",0.6f);
        Product actual=pdao.save(p1);
        assertEquals(actual.toString(),p1.toString());
    }
	  @Test
	    void testGetAllProductsServ() {
	        Product p1=new Product("bread",30f,22,"food",0.6f);
	        pdao.save(p1);
	        Product p2=new Product("eggs",40f,50,"food",0.8f);
	        pdao.save(p2);
	        long actual=2;
	        long expected=pdao.count();
	        assertEquals(actual, expected);
	    }
	  @Test
	    void testDeleteProductsServ() {
	        Product p1=new Product("bread",30f,22,"food",0.6f);
	        pdao.save(p1);
	        pdao.delete(p1);
	        int actual=0;
	        int expected=pdao.countByProductId(p1.getProductId());
	        assertEquals(actual, expected);
	    }
	  @Test
	    void testUpdateProdServ() {
	        Product p1=new Product("bread",30f,22,"food",0.6f);
	        pdao.save(p1);
	        Product p2=new Product("brown bread",30f,52,"food",0.6f);
	        Product actual=pdao.saveAndFlush(p2);
	        assertEquals(actual.toString(),p2.toString());
	    }
	/*  @Test
	    void testDeleteUserServ(){
	        UserData u1=new UserData("alex","alex@gmail.com","mumbai","mumbai");
	        userdao.save(u1);
	        int id=u1.getUserId();
	        aser.deleteUsers(u1.getUserId());
	        int actual=0;
	        int expected=userdao.countByUserId(id);
	        assertEquals(actual, expected);

	    }*/
	  @Test
	    void testBlockUserServ(){
	        UserData u1=new UserData("alex","alex@gmail.com","mumbai","mumbai");
	        userdao.save(u1);
	        aser.blockUsers(u1.getUserId());
	        String actual="Blocked";
	        String expected=userdao.findById(u1.getUserId()).get().getAccStatus();
//	        String expected=udao.getById(u1.getUserId()).getAccStatus();
	        assertEquals(actual, expected);

	    }
	  @Test
		void testAddAdminRest() throws URISyntaxException, JsonProcessingException, AdminExistsException{
			final String url="http://localhost:8900/addadmin";
			Admin a1=new Admin("admin_2","admin123");
			//aser.addAdmin(a1);
			URI uri=new URI(url);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Admin> request = new HttpEntity<>(a1, headers);
			RestTemplate template=new RestTemplate();
			ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		    assertEquals(HttpStatus.OK,res.getStatusCode());
		}
	  @Test
		void testFindAllAdminRest() throws URISyntaxException, JsonProcessingException {
		  	Admin a=new Admin("John","1234");
		  	admindao.save(a);
			RestTemplate template=new RestTemplate();
			final String url="http://localhost:8900/getalladmin";
			URI uri=new URI(url);
			ResponseEntity<String> res=template.getForEntity(uri,String.class);
		    assertEquals(HttpStatus.OK,res.getStatusCode());
		}
	  @Test 
		void testAdminLoginRest() throws URISyntaxException, JsonProcessingException, AdminNotFoundException, AdminExistsException {
			final String url="http://localhost:8900/adminlogin";
			Admin a1=new Admin("admin","admin123");
			//a1.setLoginStatus(false);
			aser.addAdmin(a1);
			aser.adminLogin(a1);
			URI uri=new URI(url);
			//HttpHeaders headers = new HttpHeaders();
			HttpEntity<Admin> request = new HttpEntity<>(a1);
			RestTemplate template=new RestTemplate();
			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			ResponseEntity<String>  res= template.exchange(uri,HttpMethod.PATCH,request,String.class);
			assertEquals(HttpStatus.OK,res.getStatusCode());
		}
	  @Test
		void testAdminLogoutRest() throws URISyntaxException, JsonProcessingException, AdminNotLoggedException, AdminExistsException  {
			final String url="http://localhost:8900/adminlogin";
			Admin a1=new Admin("admin_1","admin123");
			a1.setLoginStatus(true);
			aser.addAdmin(a1);
			aser.adminLogout(a1);
			URI uri=new URI(url);
			HttpEntity<Admin> request = new HttpEntity<>(a1);
			RestTemplate template=new RestTemplate();
			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			ResponseEntity<String>  res= template.exchange(uri,HttpMethod.PATCH,request,String.class);
			assertEquals(HttpStatus.OK,res.getStatusCode());
		
		}
	  @Test
		void testAddProdRest() throws URISyntaxException, JsonProcessingException {
			RestTemplate template=new RestTemplate();
			final String url="http://localhost:8900/addproduct";
			Product p1=new Product("bread",30f,22,"food",0.6f);
			aser.addProducts(p1);
			URI uri=new URI(url);
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<Product> request = new HttpEntity<>(p1, headers);
			ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
		    assertEquals(HttpStatus.OK,res.getStatusCode());

		}
		
		@Test
		void testFindAllProdRest() throws URISyntaxException, JsonProcessingException {
			Product p=new Product("coffee",20f,4,"edibles",10f);
			pdao.save(p);
			RestTemplate template=new RestTemplate();
			final String url="http://localhost:8900/getallproduct";
			URI uri=new URI(url);
			ResponseEntity<String> res=template.getForEntity(uri,String.class);
		    assertEquals(HttpStatus.OK,res.getStatusCode());
		}
		@Test
		void testDeleteProdRest() throws URISyntaxException, JsonProcessingException {
			Product p1=new Product("bread",30f,22,"food",0.6f);
			aser.addProducts(p1);
			//aser.deleteProducts(p1.getProductId());
			int pid=p1.getProductId();
			final String url="http://localhost:8900/deleteproduct/"+pid;
			URI uri=new URI(url);
			//HttpHeaders headers = new HttpHeaders();
			HttpEntity<Product> request = new HttpEntity<>(p1);
			RestTemplate template=new RestTemplate();
			ResponseEntity<String>  res=template.exchange(uri, HttpMethod.DELETE, request, String.class);
			assertEquals(HttpStatus.OK,res.getStatusCode());
		
		}
		@Test
		void testUpdateProdRest()throws URISyntaxException ,JsonProcessingException {
			Product p1=new Product("bread",30f,22,"food",0.6f);
			aser.addProducts(p1);
			//dao.save(p1);
			Product p2=new Product("brown bread",30f,52,"food",0.6f);
			p2.setProductId(p1.getProductId());
			aser.updateProducts(p2.getProductId(),p2);
			//dao.saveAndFlush(p2);
			int pid=p2.getProductId();
			final String url="http://localhost:8900/updateproduct/"+pid;
			URI uri=new URI(url);
			HttpEntity<Product> request = new HttpEntity<Product>(p2);
			RestTemplate template=new RestTemplate();
			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			ResponseEntity<String>  res= template.exchange(uri,HttpMethod.PATCH,request,String.class);
			assertEquals(HttpStatus.OK,res.getStatusCode());
			
			//Assertions.assertEquals("updated",template.exchange(uri,HttpMethod.PATCH,request,String.class).getBody());
		}
		
		@Test
		void testBlockUserRest() throws URISyntaxException,JsonProcessingException {
			UserData u1=new UserData("alex","alex@gmail.com","mumbai","mumbai");
			userv.addUser(u1);
			int uid=u1.getUserId();
			final String url="http://localhost:8900/blockuser/"+uid;
			URI uri=new URI(url);
			HttpEntity<UserData> request = new HttpEntity<UserData>(u1);
			RestTemplate template=new RestTemplate();
			template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
			ResponseEntity<String>  res= template.exchange(uri,HttpMethod.PATCH,request,String.class);
			assertEquals(HttpStatus.OK,res.getStatusCode());
			
		}
		@Test
		void testGetAllUserByAdminController() throws Exception
		{
			UserData u1=new UserData("alex","alex@gmail.com","mumbai","mumbai");
			userv.addUser(u1);
			List<UserData> list=userv.getAllUser();
			ResponseEntity<Object> res=adminController.getAllUserByAdmin();
			assertEquals(res.getBody().toString(),list.toString());
		}
		@Test
		void testGetAllUserByAdminControllerFail()
		{
			userdao.deleteAll();
			Exception exception= assertThrows(ListEmptyException.class, ()->{
	             adminController.getAllUserByAdmin();
		});
			 String expectedMessage = 
                     "List is empty";
             String actualMessage = exception.toString();
             assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testSetOrderStatusController() throws Exception
		{
			UserData u1=new UserData("alex","alex@gmail.com","mumbai","mumbai");
			userv.addUser(u1);
			Cart c=cserv.findCartOfUser(u1);
			OrderData o=new OrderData(new java.util.Date(),40f ,"Rec",c );
			oservice.addOrder(o);
			ResponseEntity<String> res=adminController.setStatus(o.getOrderId(),"Ordered");
			assertEquals("Status Updated", res.getBody());
		}
		@Test
		void testSetOrderStatusControllerFail() throws Exception
		{
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.setStatus(100,"Ordered");
		});
			String expectedMessage = 
                    "Order Not Found";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testdeleteOrderController() throws Exception
		{
			OrderData o=new OrderData();
			orderdao.save(o);
			ResponseEntity<String> res=adminController.deleteOrder(o);
			assertEquals("Order Deleted", res.getBody());
		}
		@Test
		void testdeleteOrderControllerFail()
		{
			OrderData o=new OrderData();
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.deleteOrder(o);
		});
			String expectedMessage = 
                    "Order Not Found";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testAddAdminController() throws Exception
		{
			Admin a=new Admin("John", "1234");
			ResponseEntity<String> res=adminController.addadmin(a);
			assertEquals("Admin added", res.getBody());
		}
		@Test
		void testAddAminControllerFail()
		{
			Admin a=new Admin();
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.addadmin(a);
		});
			String expectedMessage = 
                    "Object could not be Added.";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testAdminLoginController() throws Exception
		{
			Admin a=new Admin("john", "1234");
			admindao.save(a);
			ResponseEntity<String> res=adminController.adminlogin(a);
			assertEquals("Admin logged in successfully", res.getBody());
		}
		@Test
		void testAdminLoginControllerFail()
		{
			Admin a=new Admin();
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.adminlogin(a);
		});
			String expectedMessage = 
                    "Admin Not Found";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testAdminLogoutController() throws Exception
		{
			Admin a=new Admin("John","134");
			admindao.save(a);
			adminController.adminlogin(a);
			ResponseEntity<String> res=adminController.adminlogout(a);
			assertEquals("Admin logged out successfully", res.getBody());
		}
		@Test
		void testAdminLogoutControllerFail()
		{
			Admin a=new Admin("John","134");
			admindao.save(a);
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.adminlogout(a);
		});
			String expectedMessage = 
                    "Admin has not logged in.";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testGetAllAdminController() throws Exception
		{
			Admin a=new Admin("John","134");
			admindao.save(a);
			List<Admin> exp=admindao.findAll();
			List<Admin> act=adminController.getalladmin();
			assertEquals(exp.toString(), act.toString());
		}
		@Test
		void testGetAllAdminControllerFail()
		{
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.getalladmin();
		});
			String expectedMessage = 
                    "List is Empty.";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testBlockUserController() throws Exception
		{
			UserData u=new UserData("john", "mail", "billing Add", "goa");
			userdao.save(u);
			int uid=u.getUserId();
			ResponseEntity<String> res=adminController.blockUser(uid);
			assertEquals("User blocked", res.getBody());
		}
		@Test
		void testBlockUserControllerFail()
		{
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.blockUser(0);
		});
			String expectedMessage = 
                    "User Not Found";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testAddProductController() throws Exception
		{
			Product p=new Product("coffe", 40f, 10, "ediles", 10f);
			ResponseEntity<String> res=adminController.addproduct(p);
			assertEquals(res.getBody(), "Product added successfully");
		}
		@Test
		void testAddProductControllerFail()
		{
			Product p=new Product();
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.addproduct(p);
		});
			String expectedMessage = 
                    "Object could not be Added.";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testGetAllProductController() throws Exception
		{
			Product p=new Product("coffe", 40f, 10, "ediles", 10f);
			pdao.save(p);
			List<Product> act=pdao.findAll();
			List<Product> exp=adminController.getallproduct();
			assertEquals(act.toString(), exp.toString());
		}
		@Test
		void testGetAllProductControllerFail()
		{
			Exception exception= assertThrows(Exception.class, ()->{
				adminController.getallproduct();
		});
			String expectedMessage = 
                    "List is Empty.";
            String actualMessage = exception.toString();
			assertEquals(expectedMessage, actualMessage);
		}
		@Test
		void testUpdateProductController() throws Exception
		{
			Product p=new Product("coffe", 40f, 10, "ediles", 10f);
			pdao.save(p);
			int pid=p.getProductId();
			ResponseEntity<String> res=adminController.updateProduct(pid, p);
			assertEquals("Product updated successfully", res.getBody());
		}
		@Test
		void testDeleteProductController() throws Exception
		{
			Product p=new Product("coffe", 40f, 10, "ediles", 10f);
			pdao.save(p);
			int pid=p.getProductId();
			ResponseEntity<String> res=adminController.deleteProduct(pid);
			assertEquals("Product deleted successfully", res.getBody());
		}
		@Test
        void testGetProductByIdServ() {
            Product p1=new Product("bread",30f,22,"food",0.6f);
            pdao.save(p1);
            Product expected=pdao.findById(p1.getProductId()).get();
            Product actual=aser.getProductsById(p1.getProductId());
            assertEquals(expected.toString(),actual.toString());
        }
		@Test
	    public void testAdminLoginServFailed() throws AdminNotFoundException{
	        Admin a1=new Admin("admin","admin123");
	        Exception exception= assertThrows(AdminNotFoundException.class, ()->{
	            aser.adminLogin(a1);



	       });
	            String expectedMessage = "Admin Not Found";
	            String actualMessage = exception.toString();
	            System.out.println(expectedMessage);
	            System.out.println(actualMessage);
	            assertTrue(actualMessage.equals(expectedMessage));
	            
	    }
		@Test
	    public void testAddAdminServFailed() throws AdminExistsException{
	        Admin a1=new Admin("admin","admin123");
	        admindao.save(a1);
	        Exception exception= assertThrows(AdminExistsException.class, ()->{
	            aser.addAdmin(a1);



	       });
	            String expectedMessage = "Admin Already Exists.";
	            String actualMessage = exception.toString();



	           assertTrue(actualMessage.equals(expectedMessage));
	    }
}
