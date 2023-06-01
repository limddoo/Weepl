package com.weepl.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MhinfoSearchDto {
	private String searchByCate;
	private String searchByLike;
	private String searchQuery="";

}
