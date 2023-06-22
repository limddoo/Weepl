package com.weepl.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.weepl.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NoticeFormDto {
	
	// 게시글 고유번호
	private Long cd;
	
	// 공지사항 제목
	@NotBlank(message="공지사항 제목을 입력해주십시오.")
	private String title;
	
	// 공지사항 내용
	@NotBlank(message="공지사항 내용을 입력해주십시오.")
	private String content;

	// 게시글 등록일자
	private LocalDateTime regDt;
	
	// 이미지를 저장하는 리스트
	private List<BoardImgDto> boardImgDtoList = new ArrayList<>(); //공지사항 저장 후 수정할때 이미지 정보를 저장하는 리스트
	
	// 이미지 번호를 저장하는 리스트
	private List<Long> boardImgCds = new ArrayList<>();
	
	// 첨부파일 저장하는 리스트
	private List<BoardAttachDto> boardAttachDtoList = new ArrayList<>();
	
	// 첨부파일 번호를 저장하는 리스트
	private List<Long> boardAttachCds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Notice noticeFormDtoToNotice() { //반환 타입이 entity
		return modelMapper.map(this, Notice.class);   // .map(source, destination)
		//dto -> entity
	}
	
	public static NoticeFormDto noticeToNoticeFormDto(Notice notice) { //반환 타입이 Dto
		return modelMapper.map(notice, NoticeFormDto.class);
		//entity -> dto
	}

}
