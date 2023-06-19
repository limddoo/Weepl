package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.ReserveSchedule;

import lombok.Data;

@Data
public class ReserveScheduleDto {
	
	private Long cd;
	private String reserveDate;
	private String reserveTime;
	private String status;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static ReserveScheduleDto of(ReserveSchedule reserveSchedule) {
		return modelMapper.map(reserveSchedule, ReserveScheduleDto.class);
		//entity -> dto
	}
}
