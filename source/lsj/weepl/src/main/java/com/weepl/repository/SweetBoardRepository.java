package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.weepl.entity.SweetBoard;

public interface SweetBoardRepository extends JpaRepository<SweetBoard, Long>,
					QuerydslPredicateExecutor<SweetBoard>, SweetRepositoryCustom {
	
	List<SweetBoard> findByTitle(String title);
	SweetBoard findByCd(Long cd);
	
	@Modifying
	@Query("update SweetBoard sb set sb.like_cnt = sb.like_cnt + 1 where sb.cd = :cd")
	int addLike(Long cd);
}