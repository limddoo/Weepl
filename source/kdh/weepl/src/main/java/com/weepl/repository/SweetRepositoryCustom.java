package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SweetSearchDto;
import com.weepl.entity.SweetBoard;

public interface SweetRepositoryCustom {
	
	Page<SweetBoard> getSweetBoardPage(SweetSearchDto sweetSearchDto, Pageable pageable);
}