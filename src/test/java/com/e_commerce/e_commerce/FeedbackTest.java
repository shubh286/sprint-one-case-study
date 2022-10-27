package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.AdminDAO;
import com.dao.AdminFeedbackDAO;
import com.dao.FeedbackDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Admin;
import com.model.AdminFeedback;
import com.model.Feedback;
import com.model.GettingFeed;
import com.model.Product;
import com.model.UserData;
import com.service.FeedbackService;

@SpringBootTest
class FeedbackTest {

	@Autowired
    FeedbackService testfeedbackservice;
    
    @Autowired
    FeedbackDAO feeddao;
    
    @Autowired
    AdminFeedbackDAO adminfeeddao;
    @Autowired
    AdminDAO adao;
	@Test
    void testGetFeedback() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/savefeed";
      URI uri=new URI(url);
      GettingFeed feedback = new GettingFeed();
      Product p  =new Product();
      Feedback f  = new Feedback();
      UserData u = new UserData();
      feedback.setF(f);
      feedback.setP(p);
      feedback.setU(u);
      HttpHeaders headers = new HttpHeaders();
      HttpEntity<GettingFeed> request = new HttpEntity<>(feedback, headers);
      
      ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    
    @Test
    void testAdminFeedback()  throws URISyntaxException, JsonProcessingException {
    	
        RestTemplate template=new RestTemplate();
        
        Feedback testFeed  = new Feedback();
        testfeedbackservice.saveFeedback(testFeed);
        int id = testFeed.getFeedbackId();
        Admin testAdmin = new Admin();
        adao.save(testAdmin);
        AdminFeedback testAdminFeed = new AdminFeedback();
        testAdminFeed.setAdminId(testAdmin);
        
        final String url="http://localhost:8900/admin/giveFeedback/"+id;
        URI uri=new URI(url);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<AdminFeedback> request = new HttpEntity<>(testAdminFeed, headers);
          
        ResponseEntity<String> res=template.postForEntity(uri,request,String.class);
        assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    
    @Test
    void testGetAllFeedback() throws URISyntaxException, JsonProcessingException {
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8900/getallfeedback";
      URI uri=new URI(url);
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      assertEquals(HttpStatus.OK,res.getStatusCode());
    }
    
    
    @Test
    void serviceSaveFeedback() {
        
        Feedback myfeed = new Feedback();
        Feedback testfeed = feeddao.save(myfeed);
        assertEquals(testfeed,myfeed);
    }
    
    @Test
    void serviceSaveadminFeedback() {
        
        AdminFeedback myadminfeed = new AdminFeedback();
        AdminFeedback testadminfeed = adminfeeddao.save(myadminfeed);
        assertEquals(testadminfeed,myadminfeed);
    }
    
    @Test
    void servicegetFeedback() {
        
        List<Feedback> listFeed = testfeedbackservice.getFeedback();
        assertEquals(feeddao.findAll().toString(),listFeed.toString());
    }
}
