package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsNmem;

public interface BoardConsNmemRepository extends JpaRepository<BoardConsNmem, Long> {
	BoardConsNmem findByName(String name);
	BoardConsNmem findByBoardConsCd(Long cd);

}
