package com.e_commerce.e_commerce;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dao.ProductDAO;
import com.model.Product;
import com.service.ProductService;

@SpringBootTest
class ProductTest {
	@Autowired
	ProductService productservice;
	@Autowired
	ProductDAO productdao;
	Product p;
    @BeforeEach
    void init() {
        p=new Product("Coffee",120f,10,"Eatables",0.30f);



   }
    
    @Test
    void testUpdateProduct(){
        productservice.addProduct(p);
        Product pro1=new Product(2,"Coffee",130f,8,"Eatables",0.20f);
        productservice.updateProduct(pro1);
        assertNotNull(productdao.findById(pro1.getProductId()));



   }
     @Test
        void testGetAllProductsService() {
         Product pro2=new Product("Tea",30f,80,"Eatables",0.10f);
             productservice.addProduct(pro2);
            long c=productdao.count();
            List<Product> ocnt=productservice.getAllProduct();
            assertEquals(ocnt.size(),c);
        }



    @Test
     void testDeleteProductService() {
         productservice.addProduct(p);
         productservice.deleteProduct(p.getProductId());
         //productdao.deleteById(p.getProductId());
         List<Product> actual=productservice.getAllProduct();
//         long actual=1;
         long expected=productdao.count();
//         Assertions.assertNull(p);
         assertEquals(actual.size(),expected);
     }
	

}
