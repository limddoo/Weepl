package com.weepl.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SearchDto;
import com.weepl.entity.ReserveApply;

public interface ReserveApplyRepositoryCustom {
	List<ReserveApply> getReserveApplyList(SearchDto searchDto, String status);
}
