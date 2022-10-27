package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.AdminFeedback;
@Repository
public interface AdminFeedbackDAO extends JpaRepository<AdminFeedback, Integer>{

}
