package com.weepl.dto;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MhinfoDto {
	private Long cd;
	private String title;
	private String content;
	private int likeCnt;
	// 게시글 등록일자
	private LocalDateTime regDt;
//	Mhinfo existsByTitle(String title);
}
