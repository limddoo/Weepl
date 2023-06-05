package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.BoardImg;

import lombok.Data;

@Data
public class NoticeImgDto {
	
	private Long imgCd;
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static NoticeImgDto boardImgToBoardImgDto(BoardImg boardImg) {
		return modelMapper.map(boardImg, NoticeImgDto.class);
		//entity -> dto
	}
}
