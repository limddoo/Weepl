package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class ReserveApply {
	@Id
	@Column(name="reserve_apply_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;
	
	@Column(nullable=false)
	private LocalDateTime reserveDt;
	
	@Column(nullable=false)
	private String status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reserve_schedule_cd")
	private ReserveSchedule reserveSchedule;
	
	private String roomId;
	@Column(nullable=false)
	private String consDiv;
	
	@Lob
	@Column(nullable=false)
	private String consReqContent;
	
	private LocalDateTime cancDt;
}
