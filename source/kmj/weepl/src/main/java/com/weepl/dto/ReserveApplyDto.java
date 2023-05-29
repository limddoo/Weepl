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
	private LocalDateTime reserveDt;
	private String consDiv;
	private String consReqContent;
	private String reserveStatus;
	private LocalDateTime cancDt;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public ReserveApply reserveApplyDtotoReserveApply() {
		return modelMapper.map(this, ReserveApply.class);
		//dto -> entity
	}
	
	public static ReserveApplyDto reserveApplytoReserveApplyDto(ReserveApply reserveApply) {
		return modelMapper.map(reserveApply, ReserveApplyDto.class);
	}
}
