package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.model.UserData;
@Repository
public interface UserDataDAO extends JpaRepository<UserData, Integer>{

	public UserData findByUserName(String userName);

	public int countByUserId(int userId);
}
