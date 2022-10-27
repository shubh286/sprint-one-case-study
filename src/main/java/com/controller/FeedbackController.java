package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appexception.FeedbackNotFoundException;
import com.model.AdminFeedback;
import com.model.Feedback;
import com.model.GettingFeed;
import com.service.FeedbackService;

@RestController
public class FeedbackController {

	@Autowired
    FeedbackService feedbackservice;
    
    @PostMapping("/getfeed")
    public ResponseEntity<String> getFeed(@RequestBody GettingFeed feed) {
        
        Feedback f = feed.getF();
        f.setUserId(feed.getU().getUserId());
        f.setProductId(feed.getP().getProductId());
        feedbackservice.saveFeedback(f);
        return new ResponseEntity<String>("feedback added", HttpStatus.OK);
        
    }   
    @GetMapping("/getallfeedback")
    public List<Feedback> getAllFeedback() {
        
        return feedbackservice.getFeedback();
            
    }
    
    @PostMapping("/admin/giveFeedback/{feedbackid}")
    public ResponseEntity<String> adminFeedback(@PathVariable int feedbackid, @RequestBody AdminFeedback adminfeed) throws FeedbackNotFoundException {



       ResponseEntity<String> r = feedbackservice.adminfeedback(feedbackid, adminfeed);
        return r;
   }
    
    
//    @DeleteMapping("/admin/deletefeedback/{feedbackid")
//    public ResponseEntity deleteFeedback(@PathVariable int feedbackid) {
//        
//        feedbackservice.deleteFeedback(feedbackid);
//        
//        return new ResponseEntity("feedback deleted", HttpStatus.OK);
//        
//    }
}