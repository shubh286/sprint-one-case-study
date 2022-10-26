package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Cart;
import com.model.UserData;
@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {

	public Cart findByUserId(UserData userId);
	public int countByCartId(int cartId);
}
