package com.weepl.dto;

import org.modelmapper.ModelMapper;

import com.weepl.entity.WeeNetwork;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class WeeNetworkFormDto {

	private Long cd;
	private String locName;
	private String agencyName;
	private String agencyTel;
	private String addrDtl;
	private Float lati;
	private Float longi;
	private String url;
	
	public WeeNetworkFormDto() {
	}
	
	private static ModelMapper modelMapper = new ModelMapper();

	public WeeNetwork createWeeNetwork() {
		return modelMapper.map(this, WeeNetwork.class);
	}
	
	public static WeeNetworkFormDto of(WeeNetwork weeNetwork) {
		return modelMapper.map(weeNetwork, WeeNetworkFormDto.class);
	} // 
	
	
}
