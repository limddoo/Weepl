package com.weepl.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class BoardConsDto {
	private Long cd;
	private String pwd;
	private String title;
	private String content;
	private LocalDateTime reg_dt;
	private LocalDateTime mod_dt;
	private String del_yn;
	private String res_yn;
	private String name;
	
	private String gender;
	
	private String email;
	
	private String region;

}
