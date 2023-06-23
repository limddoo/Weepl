package com.weepl.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class NoticeDto {
	private Long noticeCd;
	private String title;
	private String createdBy;
	private LocalDateTime modDt;
	private LocalDateTime regDt;
	
	
}
