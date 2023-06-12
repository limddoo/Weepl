package com.weepl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SweetSearchDto {

	private String searchDateType;		// 게시글 등록일자별 조회
	
	private String searchBoardDiv;		// 게시판 구분별 조회
	
	private String searchBy;			// 검색 유형별 조회
	
	private String searchQuery = "";	// 검색어 저장
}
