package com.weepl.dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.querydsl.core.annotations.QueryProjection;
import com.weepl.entity.Mhinfo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class MhinfoDto {
	
	private Long cd;
	private String title;
	private String content;
	private int likeCnt;
	private LocalDateTime regDt;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	@QueryProjection
	public MhinfoDto(Long cd, String title, String content, int likeCnt, LocalDateTime regDt) {
		this.cd = cd;
		this.title = title;
		this.content = content;
		this.likeCnt = likeCnt;
		this.regDt = regDt;
	}
	
	public MhinfoDto() {
		
	}
	
	public static MhinfoDto of(Mhinfo mhinfo) {
		return modelMapper.map(mhinfo, MhinfoDto.class);
	} // entity -> dto
	
	public Mhinfo createMhinfo() {
		return modelMapper.map(this, Mhinfo.class);
	} // dto -> entity
//	Mhinfo existsByTitle(String title);
}
