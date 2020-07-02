package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.Sector;

public interface ClimbSectorRepository extends JpaRepository<Sector,Integer>{
	
	List<Sector> findSectorsByAreaId(Long areaId);
	List<Sector> findAll();
	Sector findOneById(Long sectorId);

}
