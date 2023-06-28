package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.BoardAttach;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardAttachDto {
	
	// 첨부파일 고유번호
	private Long cd;
	
	// 첨부파일명
	private String attachName;
	
	// 원본 첨부파일명
	private String oriAttachName;
	
	// 첨부파일 조회경로
	private String attachUrl;
	
	// 게시판 구분
	private String board_div;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static BoardAttachDto of(BoardAttach boardAttach) {
		return modelMapper.map(boardAttach, BoardAttachDto.class);
	}
}
