package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.dto.BoardConsFormDto;
import com.weepl.entity.BoardCons;

public interface BoardConsRepository extends JpaRepository<BoardCons, Long>, BoardConsRepositoryCustom {
	BoardCons findByCd(Long cd);
	public Long deleteByCd(Long cd);
	

}
