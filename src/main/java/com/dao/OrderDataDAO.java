package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Cart;
import com.model.OrderData;
@Repository
public interface OrderDataDAO extends JpaRepository<OrderData, Integer> {

	public OrderData findByCartId(Cart cartId);
}
