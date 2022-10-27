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

import com.controller.FeedbackController;
import com.dao.AdminDAO;
import com.dao.AdminFeedbackDAO;
import com.dao.FeedbackDAO;
import com.dao.ProductDAO;
import com.dao.UserDataDAO;
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
    UserDataDAO udao;
    @Autowired
    ProductDAO pdao;
    @Autowired
    AdminFeedbackDAO adminfeeddao;
    @Autowired
    AdminDAO adao;
    @Autowired
    FeedbackController fcontroller;
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
        testfeedbackservice.saveFeedback(myfeed);
        Feedback f=feeddao.findById(myfeed.getFeedbackId()).get();
        assertEquals(f.toString(),myfeed.toString());
    }
    
    @Test
    void serviceSaveadminFeedback() throws Exception {
        
        AdminFeedback myadminfeed = new AdminFeedback();
        Feedback myfeed=new Feedback();
        feeddao.save(myfeed);
        testfeedbackservice.adminfeedback(myfeed.getFeedbackId(), myadminfeed);
        AdminFeedback exp=adminfeeddao.findById(myadminfeed.getAdminFeedbackId()).get();
        assertEquals(exp.toString(),myadminfeed.toString());
    }
    
    @Test
    void servicegetFeedback() {
        
        List<Feedback> listFeed = testfeedbackservice.getFeedback();
        assertEquals(feeddao.findAll().toString(),listFeed.toString());
    }
    
    @Test
    void testSaveFeedController()
    {
    	Product p=new Product("Coffee",10f,10,"edibles",10f);
    	pdao.save(p);
    	UserData u=new UserData("John", "mail", "delhi", "Goa");
    	udao.save(u);
    	Feedback f=new Feedback(u.getUserId(),p.getProductId(),"good",10);
    	feeddao.save(f);
    	GettingFeed gf=new GettingFeed(p,u,f);
    	ResponseEntity<String> res=fcontroller.saveFeed(gf);
    	assertEquals("feedback added", res.getBody());
    }
    @Test
    void testGetAllFeedbackController() throws Exception
    {
    	Product p=new Product("Coffee",10f,10,"edibles",10f);
    	pdao.save(p);
    	UserData u=new UserData("John", "mail", "delhi", "Goa");
    	udao.save(u);
    	Feedback f=new Feedback(u.getUserId(),p.getProductId(),"good",10);
    	feeddao.save(f);
    	GettingFeed gf=new GettingFeed(p,u,f);
    	fcontroller.saveFeed(gf);
    	List<Feedback> act=feeddao.findAll();
    	List<Feedback> exp=fcontroller.getAllFeedback();
    	assertEquals(act.toString(),exp.toString());
    }
    @Test
    void testAdminFeedbackController() throws Exception
    {
    	Product p=new Product("Coffee",10f,10,"edibles",10f);
    	pdao.save(p);
    	UserData u=new UserData("John", "mail", "delhi", "Goa");
    	udao.save(u);
    	Feedback f=new Feedback(u.getUserId(),p.getProductId(),"good",10);
    	feeddao.save(f);
    	GettingFeed gf=new GettingFeed(p,u,f);
    	fcontroller.saveFeed(gf);
    	Admin a=new Admin("john", "7890");
    	adao.save(a);
    	int fid=f.getFeedbackId();
    	AdminFeedback af=new AdminFeedback(a, "Ok Thanks");
    	ResponseEntity<String> res=fcontroller.adminFeedback(fid, af);
    	assertEquals("Admin Feedback Added", res.getBody());
    }
}
