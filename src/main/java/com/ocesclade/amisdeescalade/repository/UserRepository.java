package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.User;

public interface UserRepository extends JpaRepository<User,String> {

	User findUserByEmail (String email);
	User findUserByLastName (String lastName);
	User findOneByPseudo (String pseudo);
	User findUserById (String id);
	List<User> findAll();
	
}
