package com.weepl.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MhinfoDto {
	private Long cd;
	private String title;
	private String content;
	private int likeCnt;
	
//	Mhinfo existsByTitle(String title);
}
