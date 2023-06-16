package com.weepl.repository;

import java.util.Map;

import org.springframework.data.domain.Pageable;

public interface BoardConsRepositoryCustom {
	Map<String,Object> getBoardConsList(Pageable pageable);
	
	Map<String,Object> getMyBoardConsList(Pageable pageable, String memberId);
}
