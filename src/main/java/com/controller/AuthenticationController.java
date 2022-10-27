package com.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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
	@Autowired
    HttpSession session;
    
    @PostMapping("/login")
    public ResponseEntity<String> loginuser(@RequestBody UserData user)
    {
    	Optional<UserData> userexists=userdao.findById(user.getUserId());
        if(userexists!=null)
        {
            session.setAttribute("user", userexists);
        }
        return new ResponseEntity<String>("session created",HttpStatus.ACCEPTED);
    }
    
    
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
    
    
    @PostMapping("/loginUser/{name}/{password}")
    public ResponseEntity<String> loginUser(@PathVariable String name,@PathVariable String password)throws NullValuesFoundException{
    	try {
    		List<UserData> userexists=userdao.findAll();
        	for(UserData u:userexists) {
        		if(!u.getAccStatus().equals("Blocked") &&  name!="" && password!="" && u.getUserName().equals(name) && u.getPassword().equals(password)){
        			session.setAttribute("user", userexists);
        			u.setLoginStatus(true);
        			uservice.addUser(u);
        			return new ResponseEntity<String>("Session Created! User logged in successfully",HttpStatus.OK);
        	      }
        		else if(name!="" && password!="" && !u.getUserName().equals(name) || !u.getPassword().equals(password)) {
        			return new ResponseEntity<String>("Invalid Credentials",HttpStatus.OK);
        		}
        		else {
        			throw new NullValuesFoundException();
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