package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.appexception.NullValuesFoundException;
import com.appexception.UserNotLoginException;
import com.controller.AuthenticationController;
import com.dao.UserDataDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.UserData;
import com.service.UserDataService;

@SpringBootTest
class AuthenticationTest {

	@Autowired
	AuthenticationController authcontroller;
	@Autowired
	UserDataDAO udao;
	@Autowired
	UserDataService uservice;
	@Test
    void testAddRegisteredUser() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/registeruser";
      UserData users=new UserData("Jane","1234#","jane@gmail.com","Mumbai","Mumbai",false);
      URI uri=new URI(url);
      HttpHeaders headers = new HttpHeaders();      
      HttpEntity<UserData> request = new HttpEntity<>(users, headers);
      ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testAddLoginUser() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      UserData users=new UserData("Mary","1234#","jane@gmail.com","Mumbai","Mumbai",true);
      udao.save(users);
      final String url="http://localhost:8900/loginUser";
      URI uri=new URI(url);
      HttpHeaders headers = new HttpHeaders();      
      HttpEntity<UserData> request = new HttpEntity<>(users, headers);
      ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
  }
	@Test
    void testLogout() throws URISyntaxException, JsonProcessingException{
        RestTemplate template=new RestTemplate();
        //String name="Mary";
          UserData us=new UserData("Mary","1234#","jane@gmail.com","Mumbai","Mumbai",true,"Active");
          udao.save(us);
          final String url="http://localhost:8900/logout";
          URI uri=new URI(url);
          HttpHeaders headers = new HttpHeaders();      
          HttpEntity<String> request = new HttpEntity<>(us.getUserName(), headers);
          ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
          assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    @Test
    public void logoutFail() throws NullValuesFoundException{
        UserData users = new UserData("","", "", "", "", true,"");
        Exception exception=assertThrows(NullValuesFoundException.class, ()->{
            authcontroller.logoutUser(users.getUserName());
        });
         String expectedMessage = "Null values are Found";
         String actualMessage= exception.toString();
         assertTrue(actualMessage.equals(expectedMessage));
    }
    @Test
    public void logoutInvalid() throws URISyntaxException {
        RestTemplate template=new RestTemplate();
//        UserData us=new UserData("John","1234#","jane@gmail.com","Mumbai","Mumbai",true,"Active");
//        udao.save(us);
        String name="xyz";
        final String url="http://localhost:8900/logout";
        URI uri=new URI(url);
          HttpHeaders headers = new HttpHeaders();      
          HttpEntity<String> request = new HttpEntity<>(name, headers);
          ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
          assertEquals(HttpStatus.OK,res.getStatusCode());
    }
	
	@Test
	void testAddUserController1() throws Exception{
		UserData users = new UserData("John", "1234#", "john@gmail.com", "Mumbai", "Mumbai", false);
		ResponseEntity<String> res=authcontroller.addUser(users);
		assertEquals("user added successfully", res.getBody());
	}
	
	@Test
	void testAddUserController2() throws Exception{
		UserData users = new UserData("John", "1234#", "john@gmail.com", "Mumbai", "Mumbai", true);
		ResponseEntity<String> res=authcontroller.addUser(users);
		assertEquals("You have Already Registered", res.getBody());
	}
	
	@Test
	public void addUserFail() throws Exception{
		UserData users = new UserData("", "", "", "Mumbai", "Mumbai", true);

		Exception exception=assertThrows(Exception.class, ()->{
			authcontroller.addUser(users);
		});
		 String expectedMessage = "Null values are Found";
		 String actualMessage= exception.toString();
         assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	void testAddLoginUserControllerValid() throws Exception{
		UserData users = new UserData("Jake123", "1234#", "jane@gmail.com", "Mumbai", "Mumbai", false);
		udao.save(users);
		ResponseEntity<String> res=authcontroller.loginUser(users);
		assertEquals("User Logged In", res.getBody());
	}
	
	@Test
	void testAddLoginUserControllerInvalid() throws Exception{
		UserData users = new UserData("xxx", "1234#", "jane@gmail.com", "Mumbai", "Mumbai", false);
		ResponseEntity<String> res=authcontroller.loginUser(users);
		assertEquals("Invalid Credentials", res.getBody());
	}
	
	@Test
	void testAddLoginUserControllerBlocked() throws Exception{
		UserData users = new UserData("Jake123", "1234#", "jane@gmail.com", "Mumbai", "Mumbai", false);
		uservice.addUser(users);
		users.setAccStatus("Blocked");
		ResponseEntity<String> res=authcontroller.loginUser(users);
		assertEquals("User is Blocked", res.getBody());
	}
	
	@Test
	public void loginUserFail() throws NullValuesFoundException{
		UserData users = new UserData("", "", "", "", "", true);
		Exception exception=assertThrows(NullValuesFoundException.class, ()->{
			authcontroller.loginUser(users);
		});
		 String expectedMessage = "Null values are Found";
		 String actualMessage= exception.toString();
         assertTrue(actualMessage.equals(expectedMessage));
	}
	
	@Test
	void testCheckLoginStatusControllerTrue() throws Exception{
		UserData users = new UserData("Jake", "1234#", "jake@gmail.com", "Mumbai", "Mumbai", true);
		uservice.addUser(users);
		ResponseEntity<String> res=authcontroller.checkLoginStatus("Jake");
		assertEquals("You are already Logged In.Proceed to Payment", res.getBody());
	}
	
	@Test
	public void checkLoginStatusFail() throws UserNotLoginException{
		Exception exception=assertThrows(UserNotLoginException.class, ()->{
			authcontroller.checkLoginStatus("morty");
		});
		 String expectedMessage = "User Not Logged In";
		 String actualMessage= exception.toString();
         assertTrue(actualMessage.equals(expectedMessage));
	}
	@Test
	void logoutUserSuccess() throws Exception{
		UserData users = new UserData("Jake123", "1234#", "jane@gmail.com", "Mumbai", "Mumbai", false);
		uservice.addUser(users);
		ResponseEntity<String> res=authcontroller.logoutUser(users.getUserName());
		assertEquals("User has successfully logged out", res.getBody());
	}
	
    @Test
	void logoutUserFail() throws Exception{
		ResponseEntity<String> res=authcontroller.logoutUser("morty");
		assertEquals("No such user with this username has signed in", res.getBody());
	}
}
