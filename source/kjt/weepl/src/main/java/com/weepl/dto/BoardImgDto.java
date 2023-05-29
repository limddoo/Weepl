package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.weepl.entity.BoardImg;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardImgDto {
	public BoardImgDto(){
		
	}
	
	private Long cd;
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static BoardImgDto of(BoardImg BoardImg) {
	return modelMapper.map(BoardImg, BoardImgDto.class);
	}

	@QueryProjection
	public BoardImgDto(Long cd, String imgName) {
		this.cd = cd;
		this.imgName = imgName;
	}
}
