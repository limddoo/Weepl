package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.weepl.dto.WeeNetworkFormDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "weeNetwork")
@Getter @Setter
@ToString
public class WeeNetwork {

	@Id
	@Column(name = "agency_cd")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;
	
	@Column(nullable = true)
	private String locName;
	
	@Column(nullable = true)
	private String agencyName;
	
	@Column(nullable = false)
	private String agencyTel;
	
	@Column(nullable = true)
	private String addrDtl;
	
	@Column(nullable = true)
	private Double lati;
	
	@Column(nullable = true)
	private Double longi;
	
	@Column(nullable = false)
	private String url;
	
	public static WeeNetwork createAgency(WeeNetworkFormDto weeNetworkFormDto) {
		WeeNetwork weeNetwork = new WeeNetwork();
		weeNetwork.setLocName(weeNetworkFormDto.getLocName());
		weeNetwork.setAgencyName(weeNetworkFormDto.getAgencyName());
		weeNetwork.setAddrDtl(weeNetworkFormDto.getAddrDtl());
		weeNetwork.setAgencyTel(weeNetworkFormDto.getAgencyTel());
		weeNetwork.setUrl(weeNetworkFormDto.getUrl());
		weeNetwork.setLati(weeNetworkFormDto.getLati());
		weeNetwork.setLongi(weeNetworkFormDto.getLongi());
		
		return weeNetwork;
	}
}