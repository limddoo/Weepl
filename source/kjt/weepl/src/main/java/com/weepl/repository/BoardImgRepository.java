package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.weepl.dto.BoardImgDto;
import com.weepl.entity.BoardImg;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {
	List<BoardImg> findByMhinfoCdOrderByCdAsc(Long mhinfoCd);
	BoardImg findByMhinfoCdAndRepimgYn(Long mhinfoCd, String repimgYn);
	
	@Query("select new com.weepl.dto.BoardImgDto(cd, imgName) from BoardImg where mhinfo_Cd = :cd") //cd는 entity mhinfo_Cd는 컬럼명
	List<BoardImgDto> getLists(Long cd);
	//mhinfo를 조건으로 해당 데이터를 List로 불러오는 쿼리 구현
}
