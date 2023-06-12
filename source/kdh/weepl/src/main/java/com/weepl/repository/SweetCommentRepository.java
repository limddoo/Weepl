package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.weepl.entity.SweetComment;

public interface SweetCommentRepository extends JpaRepository<SweetComment, Long> {
	
	List<SweetComment> findByCdOrderByCdAsc(Long cd);
	List<SweetComment> findBySweetBoardCdOrderByCdDesc(Long cd);
	
	@Query("select c from SweetComment c where c.sweetBoard.cd = :cd")
	SweetComment findByCd(Long cd);
}