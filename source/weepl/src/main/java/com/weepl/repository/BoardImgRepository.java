package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weepl.dto.BoardImgDto;
import com.weepl.entity.BoardImg;

@Repository
public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {

	List<BoardImg> findByNotice_CdOrderByCdAsc(@Param(value = "notice_cd") Long noticeCd);

	@Query("select b from BoardImg b where b.notice.cd=:noticeCd order by b.cd asc")
	List<BoardImg> findByNoticeCd(@Param("noticeCd") Long noticeCd);

	List<BoardImg> findByMhinfoCdOrderByCdAsc(Long mhinfoCd);

	BoardImg findByMhinfoCdAndRepImgYn(Long mhinfoCd, String repimgYn);

	@Query("select new com.weepl.dto.BoardImgDto(cd, imgName) from BoardImg where mhinfo_Cd = :cd") // cd�� entity
	List<BoardImgDto> getLists(Long cd);
	
	List<BoardImg> findBySweetBoardCdOrderByCdAsc(Long cd);
}
