package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Card;
@Repository
public interface CardDAO extends JpaRepository<Card, Integer>{

}
