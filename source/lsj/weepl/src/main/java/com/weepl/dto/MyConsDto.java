package com.weepl.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class MyConsDto {
	private Long cd;
	private String title;
	private String content;
	private LocalDateTime reg_dt;
	private String res_yn;
}
