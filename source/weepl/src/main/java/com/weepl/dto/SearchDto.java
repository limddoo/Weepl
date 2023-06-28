package com.weepl.dto;

import lombok.Data;

@Data
public class SearchDto {
	private String searchByCate; // 마음건강정보용 카테고리 검색
	private String searchByStatus; // 비대면 상담 내역용 status 검색 
	private String searchBoardDiv; // 스윗 게시판용 구분별 조회
	private String searchDateType; // 현재 시간과 등록일을 비교해서 데이터를 조회
	private String searchByLike;
	private String searchBy; //조회할때 어떤 유형으로 조회할지 선택한다
	private String searchQuery=""; //조회할 검색어 저장할 변수
}
