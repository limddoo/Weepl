package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.weepl.constant.MhinfoCate;

public class MhninfoCate {

	@Id
	@Column(name = "cate_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	@Column(name = "mhinfo_cate")
	@Enumerated(EnumType.STRING)
	private MhinfoCate cate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mhinfo_cd")
	private Mhinfo mhinfo;
}
