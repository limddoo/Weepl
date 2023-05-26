package com.weepl.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainMhinfoDto {

	private Long cd;
	private String title;
	private String content;
	private String imgUrl;
	
	@QueryProjection
	public MainMhinfoDto(Long cd, String title, String content, String imgUrl) {
		this.cd = cd;
		this.title = title;
		this.content = content;
		this.imgUrl = imgUrl;
	}
}
