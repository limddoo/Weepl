package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weepl.entity.BoardImg;

@Repository
public interface BoardImgRepository extends JpaRepository<BoardImg, Long>{

	
	  List<BoardImg> findByNotice_NoticeCdOrderByImgCdAsc(@Param(value="noticeCd") Long noticeCd);
	 
	
	  @Query("select b from BoardImg b where b.notice.id=:noticeCd order by b.imgCd asc")
	  List<BoardImg> findByNoticeCd(@Param("noticeCd") Long noticeCd);
	
	
}
