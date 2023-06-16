package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.MemberSearchDto;
import com.weepl.entity.Member;

public interface MemberRepositoryCustom {
	Page<Member> getAdminMemberInfoPage(MemberSearchDto memberSearchDto, Pageable pageable);
}