package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.SearchDto;
import com.weepl.entity.Member;

public interface MemberRepositoryCustom {
	Page<Member> getAdminMemberInfoPage(SearchDto memberSearchDto, Pageable pageable);
}
