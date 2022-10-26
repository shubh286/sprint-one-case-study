package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appexception.NoCardFoundException;
import com.appexception.PaymentNotFoundException;
import com.model.Card;
import com.model.Payment;
import com.model.Product;
import com.model.UserData;
import com.service.UserDataService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
public class UserDataController {

	@Autowired
	UserDataService uservice;
	
	@GetMapping("/getusers")
	public List<UserData> getAllUser()
	{
		return uservice.getAllUser();
	}
	@PostMapping("/adduser")
	public ResponseEntity<String> addUser(@RequestBody UserData u)
	{
		uservice.addUser(u);
		return new ResponseEntity<String>("User Added",HttpStatus.OK);
	}
	@PatchMapping("/updateuser")
	public ResponseEntity<String> updateUser(@RequestBody UserData u)
	{
		uservice.updateUser(u);
		return new ResponseEntity<String>("User Updated",HttpStatus.OK);
	}
	@DeleteMapping("/deleteuser")
	public ResponseEntity<String> deleteuser(@RequestBody UserData u)
	{
		uservice.deleteUser(u);
		return new ResponseEntity<String>("User Deleted",HttpStatus.OK);
	}
	@GetMapping("/getproductbyuser")
	public List<Product> getAllProducts()
	{
		return uservice.getProductsByUser();
	}
	@PostMapping("/addproducttocartbyuser/{pid}/{cid}")
	public ResponseEntity<String> addProductToCartByUser(@PathVariable("pid") int pid,@PathVariable("cid") int cid)
	{
		uservice.addProductByUserToCart(pid, cid);
		return new ResponseEntity<String>("Product Added to Cart",HttpStatus.OK);
	}
	@PostMapping("/deleteproductfromcartbyuser/{pid}/{cid}")
	public ResponseEntity<String> deleteProductFromCartByUser(@PathVariable("pid") int pid,@PathVariable("cid") int cid)
	{
		uservice.removeProductByUserFromCart(pid, cid);
		return new ResponseEntity<String>("Product Removed From Cart",HttpStatus.OK);
	}
	@PostMapping("/updatepayment/{pid}/{oid}/{paymentmode}")
	public ResponseEntity<String> updatePaymentMethod(@PathVariable("pid") int pid,@PathVariable("oid") int oid,@PathVariable("paymentmode") String paymentMode) throws Exception
	{
		try {
		uservice.updatePayment(pid, oid, paymentMode);
		return new ResponseEntity<String>("Payment Mode Updated",HttpStatus.OK);
	}
		catch(Exception e)
		{
			throw new PaymentNotFoundException();
		}
		}
	
	@PostMapping("/addcard/{pid}/{uid}")
	public ResponseEntity<String> addCard(@PathVariable("pid") int pid,@PathVariable("uid") int uid,@RequestBody Card c)
	{
		uservice.addCardDetails(pid,uid, c);
		return new ResponseEntity<String>("Card details Added",HttpStatus.OK);
	}
	@GetMapping("/viewcards/{pid}")
	public List<Card> getCards(@PathVariable("pid") int pid) throws Exception
	{
		try {
		return uservice.viewCards(pid);
	}
		catch(Exception e)
		{
			throw new NoCardFoundException();
		}
	}
	@GetMapping("/selectcard/{pid}/{cid}")
	public Card selectCard(@PathVariable("pid") int pid,@PathVariable("cid") int cid) throws Exception
	{
		try {
		return uservice.selectCard(pid, cid);
	}
		catch(Exception e)
		{
			throw new NoCardFoundException();
		}
	}
	
	@GetMapping("/getpaymentforuser")
	public Payment getPaymentForUser(@RequestBody UserData u)
	{
		return uservice.getPaymentForUser(u);
	}
}
