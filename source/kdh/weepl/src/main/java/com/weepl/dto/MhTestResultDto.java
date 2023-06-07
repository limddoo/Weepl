package com.weepl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MhTestResultDto {
	
	private Long cd;
	private String major_div;
	private String mid_div;
	private String minor_div;
	private String result_content;
	private Long memberCd;

}
