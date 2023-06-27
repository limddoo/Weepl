package com.weepl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyConsDtlDto {
	private Long cd;
	private String title;
	private String content;
	public void setMyConsCd(Long myConsCd) {
		this.cd = myConsCd;
		
	}
}
