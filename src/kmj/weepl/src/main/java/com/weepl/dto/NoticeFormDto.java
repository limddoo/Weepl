package com.weepl.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.weepl.entity.Notice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NoticeFormDto {
	
	private Long noticeCd;
	
	@NotBlank(message="공지사항 제목을 입력해주십시오.")
	private String title;
	
	@NotBlank(message="공지사항 내용을 입력해주십시오.")
	private String content;
	
	private List<NoticeImgDto> noticeImgDtoList = new ArrayList<>(); //공지사항 저장 후 수정할때 이미지 정보를 저장하는 리스트
	
	private List<Long> noticeImgIds = new ArrayList<>();
	
	private List<NoticeAttachDto> noticeAttachDtoList = new ArrayList<>();
	
	private NoticeAttachDto noticeAttachDto = new NoticeAttachDto();
	
	private List<Long> noticeAttachIds = new ArrayList<>();
	
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
