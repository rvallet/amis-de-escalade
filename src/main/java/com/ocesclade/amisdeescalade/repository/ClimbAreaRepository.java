package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.Area;


public interface ClimbAreaRepository extends JpaRepository<Area,Integer>{
	
	List<Area> findAll();
	Area findOneById (Long id);
	List<Area> findByAuthor (String author);

}
