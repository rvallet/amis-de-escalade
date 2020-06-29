package com.ocesclade.amisdeescalade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocesclade.amisdeescalade.entities.TopoLoan;

public interface TopoLoanRepository extends JpaRepository<TopoLoan,Integer>{

	List<TopoLoan> findAll();
	List<TopoLoan> findAllByLender (String email);
	List<TopoLoan> findAllByBorrower (String email);
	TopoLoan findTopoLoanById(Long id);
}
