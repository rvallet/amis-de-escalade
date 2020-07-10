package com.ocesclade.amisdeescalade.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ocesclade.amisdeescalade.entities.Topo;

public interface TopoRepository extends JpaRepository<Topo,Integer>{

	@Query("SELECT t FROM Topo t WHERE t.isOnline = 1")
	List<Topo> findAllActiveTopos();
	
	List<Topo> findAll();
	
	List<Topo> findToposByUser_Id (Long id);
	List<Topo> findToposByUser_Email (String email);
	Topo findTopoById(long id);
	
//	Exemples :
//	  List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
//
//	  // Enables the distinct flag for the query
//	  List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
//	  List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
//
//	  // Enabling ignoring case for an individual property
//	  List<Person> findByLastnameIgnoreCase(String lastname);
//	  // Enabling ignoring case for all suitable properties
//	  List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
//
//	  // Enabling static ORDER BY for a query
//	  List<Person> findByLastnameOrderByFirstnameAsc(String lastname);
//	  List<Person> findByLastnameOrderByFirstnameDesc(String lastname);
	
}
