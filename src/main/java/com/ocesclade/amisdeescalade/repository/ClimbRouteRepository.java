package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.Route;

public interface ClimbRouteRepository extends JpaRepository<Route,Integer>{

	List<Route> findRoutesBySectorAreaId (Long areaId);
	List<Route> findAll();
	Route findOneById (Long id);
}
