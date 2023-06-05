package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.BoardCons;

public interface BoradConsRepository extends JpaRepository<BoardCons, Long> {
	BoardCons findByCd(Long cd); 
	public Long deleteByCd(Long cd);
	

}
