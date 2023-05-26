package com.weepl.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.weepl.entity.BoardCons;
import com.weepl.entity.Member;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BoardConsListDto {
	
	private Long cd;
	private String title;
	private String name;
	private LocalDateTime regDt;


}
