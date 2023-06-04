package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Float lati;
	
	@Column(nullable = true)
	private Float longi;
	
	@Column(nullable = false)
	private String url;
}