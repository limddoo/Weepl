package com.weepl.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.NoticeSearchDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.Notice;

public interface BoardConsRepositoryCustom {
	Map<String,Object> getBoardConsList(Pageable pageable);
	
	Map<String,Object> getMyBoardConsList(Pageable pageable, String memberId);
}
