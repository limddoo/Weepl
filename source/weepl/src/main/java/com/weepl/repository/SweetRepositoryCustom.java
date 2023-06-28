package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SearchDto;
import com.weepl.entity.SweetBoard;

public interface SweetRepositoryCustom {
	
	Page<SweetBoard> getSweetBoardPage(SearchDto sweetSearchDto, Pageable pageable);
}