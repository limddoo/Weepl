package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SearchDto;
import com.weepl.entity.Mhinfo;

public interface MhinfoRepositoryCustom {
	Page<Mhinfo> getMhinfoPage(SearchDto mhinfoSearchDto, Pageable pageable);
}
