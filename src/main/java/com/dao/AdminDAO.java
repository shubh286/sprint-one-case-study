package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.Admin;

@Repository
public interface AdminDAO extends JpaRepository<Admin, Integer>{

	public Admin findByAdminName(String adminName);

}
