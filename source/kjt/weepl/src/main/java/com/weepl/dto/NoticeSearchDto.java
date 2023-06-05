package com.weepl.dto;

import lombok.Data;

@Data
public class NoticeSearchDto {
	
	private String searchDateType; //현재 시간과 공지사항 등록일응 비교해서 데이터를 조회
	private String searchBy; //조회할때 어떤 유형으로 조회할지 선택한다
	private String searchQuery=""; //조회할 검색어 저장할 변수
}
