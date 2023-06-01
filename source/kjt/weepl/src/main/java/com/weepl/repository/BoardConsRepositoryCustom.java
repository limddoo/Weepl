package com.weepl.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.NoticeSearchDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.Notice;

public interface BoardConsRepositoryCustom {
	Map<String,Object> getBoardConsList(Pageable pageable);
	//조회 조건을 담고 있는 noticeSearchDto 객체와 페이징 정보를 담고 있는 pageable객체를 파라미터로 받는 getAdminPage메소드를 정의한다.
}
