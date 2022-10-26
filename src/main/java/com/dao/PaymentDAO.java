package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.OrderData;
import com.model.Payment;

@Repository
public interface PaymentDAO extends JpaRepository<Payment, Integer> {

	public Payment findByOrderId(OrderData orderId);
}
