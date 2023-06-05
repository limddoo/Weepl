package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.BoardAttach;

import lombok.Data;

@Data
public class NoticeAttachDto {

	private Long attachCd;
	private String attachName;
	private String oriAttachName;
	private String attachUrl;
	private String boardDiv;
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public NoticeAttachDto boardAttachDtoToBoardAttach() {
		return modelMapper.map(this, NoticeAttachDto.class);
		//dto -> entity
	}
	
	public static NoticeAttachDto boardAttachToBoardAttachDto(BoardAttach boardAttach) {
		return modelMapper.map(boardAttach, NoticeAttachDto.class);
		//entity -> dto
	}
	
	
}
