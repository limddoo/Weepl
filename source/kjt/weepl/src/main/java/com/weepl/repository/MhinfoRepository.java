package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.weepl.dto.MhinfoDto;
import com.weepl.entity.Mhinfo;

@Repository
public interface MhinfoRepository extends JpaRepository<Mhinfo, Long>, QuerydslPredicateExecutor<Mhinfo>, MhinfoRepositoryCustom{
	List<Mhinfo> findByTitle(String title);
	List<Mhinfo> findByTitleOrContent(String title, String content);
	
	@Modifying
	@Query("update Mhinfo m set m.view = m.view + 1 where m.cd = :cd")
	int updateView(Long cd);
	
	@Modifying
	@Query("update Mhinfo m set m.likeCnt = m.likeCnt + 1 where m.cd = :cd")
	int updateLikes(Long cd);
	
	@Query("SELECT m.cd FROM Mhinfo m ORDER BY m.cd ASC")
	List<Long> findAllMhinfoCds();
	
	@Query("SELECT m FROM Mhinfo m ORDER BY m.cd DESC")
	List<MhinfoDto> getMhinfoList();
}