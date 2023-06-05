package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.MainMhinfoDto;
import com.weepl.dto.MhinfoSearchDto;
import com.weepl.entity.Mhinfo;

public interface MhinfoRepositoryCustom {
	Page<Mhinfo> getAdminMhinfoPage(MhinfoSearchDto mhinfoSearchDto, Pageable pageable);
	Page<Mhinfo> getUserMhinfoPage(MhinfoSearchDto mhinfoSearchDto, Pageable pageable);
}
