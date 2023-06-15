package com.weepl.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.weepl.entity.Mhinfo;
import com.weepl.entity.Notice;

import lombok.Data;

@Data
public class NoticeDto {
	private Long noticeCd;
	private String title;
	private String createdBy;
	private LocalDateTime modDt;
	private LocalDateTime regDt;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	@QueryProjection
	public NoticeDto(Long noticeCd, String title, String createdBy, LocalDateTime modDt, LocalDateTime regDt) {
		this.noticeCd = noticeCd;
		this.title = title;
		this.createdBy = createdBy;
		this.modDt = modDt;
		this.regDt = regDt;
	}
	public NoticeDto() {
	    // 인자 없는 생성자 내용 (생략 가능)
	}
	public static NoticeDto of(Notice notice) {
		return modelMapper.map(notice, NoticeDto.class);
	} // entity -> dto
	
	public Notice createNotice() {
		return modelMapper.map(this, Notice.class);
	} // dto -> entity
}
