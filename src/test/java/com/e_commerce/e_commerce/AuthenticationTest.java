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

import com.dao.UserDataDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.UserData;

@SpringBootTest
class AuthenticationTest {

	@Autowired
	UserDataDAO udao;
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

}
