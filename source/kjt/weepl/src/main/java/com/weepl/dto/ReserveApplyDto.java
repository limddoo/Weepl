package com.weepl.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.weepl.entity.ReserveApply;

import lombok.Data;

@Data
public class ReserveApplyDto {
	private Long reserveApplyCd;
	private Long memCd;
	private Long reserveScheduleCd;
	private String reserveTitle;
	private String consReqContent;
	private LocalDateTime reserveDt;
	private String reserveStatus;
	private LocalDateTime cancDt;
	
	private String id;
	private String name;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ReserveApply reserveApplyDtoToReserveApply() {
		return modelMapper.map(this, ReserveApply.class);
		//dto -> entity
	}
	
	public static ReserveApplyDto reserveApplyToReserveApplyDto(ReserveApply reserveApply) {
		return modelMapper.map(reserveApply, ReserveApplyDto.class);
		//entity -> dto
	}
}
