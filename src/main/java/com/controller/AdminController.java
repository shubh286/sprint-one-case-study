package com.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appexception.AdminExistsException;
import com.appexception.AdminNotFoundException;
import com.appexception.AdminNotLoggedException;
import com.appexception.EmptyListReturnedException;
import com.appexception.ListEmptyException;
import com.appexception.ObjectAddFailException;
import com.appexception.OrderNotFoundException;
import com.appexception.ProductNotFoundException;
import com.appexception.UserNotFoundException;
import com.dao.OrderDataDAO;
import com.model.Admin;
import com.model.OrderData;
import com.model.Product;
import com.model.UserData;
import com.service.AdminService;
import com.service.OrderDataService;
import com.service.UserDataService;

@RestController
public class AdminController {

	@Autowired
	AdminService adminservice;
	@Autowired
	OrderDataService oservice;
	@Autowired
	OrderDataDAO orderdao;
	@Autowired
	UserDataService userservice;
	 @PostMapping("/addadmin")
	    public ResponseEntity<String> addadmin(@RequestBody Admin admin) throws Exception{
	        try {
	            if (admin.getAdminName() != null && admin.getAdminPassword() != null ) {
	                adminservice.addAdmin(admin);
	                return new ResponseEntity<String>("Admin added", HttpStatus.OK);
	            } else {
	                throw new ObjectAddFailException();
	            }
	        } catch (Exception e) {
	            throw new ObjectAddFailException();
	        }
	    }
	 @PatchMapping("/adminlogin")
	    public ResponseEntity<String> adminlogin(@RequestBody Admin admin) throws AdminNotFoundException {
	        try {
	            adminservice.adminLogin(admin);
	            return new ResponseEntity<String>("Admin logged in successfully", HttpStatus.OK);
	        } catch (NoSuchElementException e) {
	            throw new AdminNotFoundException();
	        }
	 
	    }
	 @PatchMapping("/adminlogout")
		public ResponseEntity<String> adminlogout(@RequestBody Admin admin)
				throws  AdminNotLoggedException {
			try {
				adminservice.adminLogout(admin);
				return new ResponseEntity<String>("Admin logged out successfully", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				throw new AdminNotLoggedException();
			}
		}
	 @GetMapping("/getalladmin")
	    public List<Admin> getalladmin() throws EmptyListReturnedException {
	        try {
	            List<Admin> alist = adminservice.getAllAdmin();
	            if (alist.size() != 0)
	                return alist;
	            else
	                throw new EmptyListReturnedException();
	        } catch (EmptyListReturnedException e) {
	            throw new EmptyListReturnedException();
	        }
	    }
	 @GetMapping("/getalluserfromadmin")
		public ResponseEntity<Object> getAllUserByAdmin() throws Exception{
			try {
				List<UserData> ulist=userservice.getAllUser();
				if(ulist.isEmpty())
					throw new ListEmptyException();
				return new ResponseEntity<>(ulist,HttpStatus.OK);
			}
			catch(Exception e) {
				throw new ListEmptyException();
			} 
		}
	 @PatchMapping("/blockuser/{uid}")
		public ResponseEntity<String> blockUser(@PathVariable int uid) throws UserNotFoundException {
			try {
				adminservice.blockUsers(uid);
				return new ResponseEntity<String>("User blocked", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				throw new UserNotFoundException();
			}
		}
	 @PostMapping("/addproduct")
		public ResponseEntity<String> addproduct(@RequestBody Product p) throws ObjectAddFailException {
			try {
				if (p.getProductName() != null && p.getCategory() != null && p.getQuantity() > 0 ) {
					adminservice.addProducts(p);
					return new ResponseEntity<String>("Product added successfully", HttpStatus.OK);
				} else {
					throw new ObjectAddFailException();
				}
			} catch (ObjectAddFailException e) {
				throw new ObjectAddFailException();
			}
		}
	 @GetMapping("/getallproduct")
		public List<Product> getallproduct() throws EmptyListReturnedException {
			try {
				List<Product> plist = adminservice.getAllProducts();
				if (plist.size() != 0)
					return plist;
				else
					throw new EmptyListReturnedException();
			}

			catch (EmptyListReturnedException e) {
				throw new EmptyListReturnedException();
			}
		}
	 @PatchMapping("/updateproduct/{pid}")
		public ResponseEntity<String> updateProduct(@PathVariable int pid, @RequestBody Product p)
				throws ProductNotFoundException {
			try {
				adminservice.updateProducts(pid, p);
				return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				throw new ProductNotFoundException();
			}
		}
	 @DeleteMapping("/deleteproduct/{pid}")
		public ResponseEntity<String> deleteProduct(@PathVariable int pid) throws ProductNotFoundException {
			try {
				adminservice.deleteProducts(pid);
				return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				throw new ProductNotFoundException();
			}
		}
	@DeleteMapping("/deleteorderbyadmin")
	public ResponseEntity<String> deleteOrder(@RequestBody OrderData p) throws Exception{
		try {
			Iterable<OrderData> iterable=orderdao.findAll();
			for(OrderData x: iterable)
				if(x.getOrderId()==p.getOrderId()) {
					oservice.deleteOrder(p);
					return new ResponseEntity<String>("Order Deleted",HttpStatus.OK);
				}
			throw new OrderNotFoundException();
		}
		catch(Exception e)
	      {
	          throw new OrderNotFoundException();
	      }
	}
	 @PostMapping("/setorderstatus/{oid}/{status}")
	    public ResponseEntity<String> setStatus(@PathVariable int oid,@PathVariable String status) throws OrderNotFoundException
	    {
	        try {
	        	oservice.setOrderStatus(oid, status);
	            return new ResponseEntity<String>("Status Updated",HttpStatus.OK);
	        }
	        catch(Exception e)
	        {
	            throw new OrderNotFoundException();
	        }
	    }
	 
	


	    
	 
	    
	/*    @GetMapping("/getproductbyid/{pid}")
		public Product getproductbyid(@PathVariable int pid) throws ProductNotFoundException{
			try {
				return adminservice.getProductsById(pid);
			}catch (NoSuchElementException e) {
				throw new ProductNotFoundException();
			}
		}*/

		

		

		
		
/*		@DeleteMapping("/deleteuser/{uid}")
		public ResponseEntity<String> deleteUser(@PathVariable int uid) throws UserNotFoundException {
			try {
				adminservice.deleteUsers(uid);
				return new ResponseEntity<String>("User Deleted", HttpStatus.OK);
			} catch (NoSuchElementException e) {
				throw new UserNotFoundException();
			}
		}*/
		

		
		
}
