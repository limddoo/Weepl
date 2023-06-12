package com.weepl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.weepl.dto.NoticeSearchDto;
import com.weepl.entity.Notice;

public interface NoticeRepositoryCustom {
	Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable);
	//조회 조건을 담고 있는 noticeSearchDto 객체와 페이징 정보를 담고 있는 pageable객체를 파라미터로 받는 getAdminPage메소드를 정의한다.
}
