package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="MhTestResult")
public class MhTestResult {
	
	@Id
	@Column(name="result_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private String major_div;
	
	private String mid_div;
	
	private String minor_div;
	
	private String result_content;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;

}
