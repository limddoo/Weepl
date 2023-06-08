package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SearchDto;
import com.weepl.entity.ReserveApply;

public interface ReserveApplyRepositoryCustom {
	Page<ReserveApply> getReserveApplyList(SearchDto searchDto, Pageable pageable);
}
