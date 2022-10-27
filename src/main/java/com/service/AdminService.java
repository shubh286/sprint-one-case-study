package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appexception.AdminExistsException;
import com.appexception.AdminNotFoundException;
import com.appexception.AdminNotLoggedException;
import com.dao.AdminDAO;
import com.dao.CartDAO;
import com.dao.ProductDAO;
import com.dao.UserDataDAO;
import com.model.Admin;
import com.model.Cart;
import com.model.Product;
import com.model.UserData;

@Service
public class AdminService {
	@Autowired
    ProductDAO pdao;
 
    @Autowired
    UserDataDAO udao;
 
    @Autowired
    AdminDAO adao;
 
    @Autowired
    CartDAO cdao;
    @Autowired
    CartService cservice;
    public List<Admin> getAllAdmin() {
        return adao.findAll();
    }
	public void addAdmin(Admin a) throws AdminExistsException {
        Admin x=adao.findByAdminName(a.getAdminName());
        if(x==null)
            adao.save(a);
        else
            throw new AdminExistsException();
    }
 
    public void addProducts(Product p) {
        pdao.save(p);
    }
 
    public List<Product> getAllProducts() {
        return pdao.findAll();
    }
 
    public Product getProductsById(int pid) {
        return pdao.findById(pid).get();
    }

    public void adminLogin(Admin admin) throws AdminNotFoundException {
    	try {
        Admin a = adao.findById(admin.getAdminId()).get();
        if (admin.getAdminName().equals(a.getAdminName()) && admin.getAdminPassword().equals(a.getAdminPassword())
&& admin.getAdminId() == a.getAdminId()) {
            admin.setLoginStatus(true);
            adao.save(admin);
        } else
            throw new AdminNotFoundException();
    	}
    	catch(Exception e)
    	{
    		throw new AdminNotFoundException();
    	}
    }
    public void adminLogout(Admin admin) throws AdminNotLoggedException {
        Admin a = adao.findById(admin.getAdminId()).get();
        if (a.isLoginStatus()) {
            a.setLoginStatus(false);
            adao.save(a);
        } else
            throw new AdminNotLoggedException();
    }
 
    public void deleteProducts(int pid) {
        Product p = pdao.findById(pid).get();
        pdao.delete(p);
    }
 
    public void updateProducts(int pid, Product product) {
        //Product p = pdao.findById(pid).get();
        product.setProductId(pid);
        pdao.saveAndFlush(product);
 
    }
 
    public void blockUsers(int uid) {
        UserData u = udao.findById(uid).get();
        u.setAccStatus("Blocked");
        udao.saveAndFlush(u);
    }
}
