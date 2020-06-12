package com.ocesclade.amisdeescalade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.User;

public interface UserRepository extends JpaRepository<User,String> {

	User findUserByEmail (String email);
	User findUserById (String id);
	
}
