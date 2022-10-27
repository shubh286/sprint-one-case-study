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

import com.appexception.NullValuesFoundException;
import com.appexception.UserNotLoginException;
import com.dao.UserDataDAO;
import com.model.UserData;
import com.service.UserDataService;

@RestController
public class AuthenticationController {

	
	@Autowired
	UserDataDAO userdao;
	@Autowired
	UserDataService uservice; 
    
    @PostMapping("/registeruser")
    public ResponseEntity<String> addUser(@RequestBody UserData users)throws NullValuesFoundException
    {
    	try {
    		if(users.isLoginStatus()==false && users.getUserName()!="" && users.getEmailId()!="") {
                uservice.addUser(users);
                return new ResponseEntity<String>("user added successfully",HttpStatus.OK);
        	}
    		else if(users.isLoginStatus()==true && users.getUserName()!="" && users.getEmailId()!=""){
    			return new ResponseEntity<String>("You have Already Registered",HttpStatus.OK);
    		}
    	}
    	catch(NullPointerException  e) {
    		throw new NullValuesFoundException();
    	}
    	return null;
    }
    
    
    @PostMapping("/loginUser")
    public ResponseEntity<String> loginUser(@RequestBody UserData u)throws NullValuesFoundException{
    	try {
    		List<UserData> userexists=userdao.findAll();
        	for(UserData i:userexists)
        	{
        		if(i.getUserName().equals(u.getUserName()))
        		{
        			if(i.getAccStatus().equals("Blocked"))
        				return new ResponseEntity<String>("User is Blocked",HttpStatus.OK);
        			if(i.getPassword().equals(u.getPassword()))
        			{
        				i.setLoginStatus(true);
        				userdao.save(i); 
        				return new ResponseEntity<String>("User Logged In",HttpStatus.OK);
        			}
        		}
        	}
        	
    	}catch(NullPointerException  e) {
    		throw new NullValuesFoundException();
    	}
		return null;
    }
    @GetMapping("/checkLoginStatus/{name}")
	public ResponseEntity<String> checkLoginStatus(@PathVariable("name") String name) throws Exception
	{
    	try
    	{
    		boolean b=uservice.checkLoginStatus(name);
    		if(b==true)
    			return new ResponseEntity<String>("You are already Logged In.Proceed to Payment",HttpStatus.OK);
    		else
    			return new ResponseEntity<String>("Please Login to Continue",HttpStatus.OK);
    	}
    	catch(Exception e)
    	{
    		throw new UserNotLoginException();
    	}
	}
}