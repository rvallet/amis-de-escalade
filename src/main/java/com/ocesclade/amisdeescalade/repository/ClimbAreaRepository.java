package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ocesclade.amisdeescalade.entities.Area;


public interface ClimbAreaRepository extends JpaRepository<Area,Integer>{
	
	List<Area> findAll();
	Area findOneById (Long id);
	List<Area> findByAuthor (String author);
	
	/* 
	 * SQL
	 * SELECT DISTINCT a.* FROM area a
		JOIN sector s ON a.id_area = s.id_area
		JOIN route r ON s.id_sector = r.id_sector
		WHERE nb_length = 1;
	 * */
	@Query("SELECT DISTINCT a from Area a JOIN Sector s ON s.area.id=a.id JOIN Route r ON r.sector.id=s.id WHERE r.nbLength = ?1")
	List<Area> findAreaBySectorAndByRouteNbLength (int routeNbLength);
	
	/* 
	 * SQL
	 * SELECT DISTINCT a.* FROM area a
		JOIN sector s ON a.id_area = s.id_area
		JOIN route r ON s.id_sector = r.id_sector
		WHERE climbing_grade LIKE '8b';
	 * */
	@Query("SELECT DISTINCT a from Area a JOIN Sector s ON s.area.id=a.id JOIN Route r ON r.sector.id=s.id WHERE r.climbingGrade LIKE %?1")
	List<Area> findAreaBySectorAndByRouteClimbingGrade (String routeClimbingGrade);

}
