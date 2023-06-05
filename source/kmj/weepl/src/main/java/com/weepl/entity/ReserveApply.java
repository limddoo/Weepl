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

import lombok.Data;

@Entity
@Table(name="reserveapply")
@Data
public class ReserveApply {

	@Id
	@Column(name="reserve_apply_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long reserveApplyCd;
	
	@ManyToOne(fetch=FetchType.LAZY)  //회원 고유번호
	@JoinColumn(name="mem_cd")
	private Member member;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reserve_schedule_cd") //예약일정 고유번호
	private ReserveSchedule reserveSchedule;
	
	@Column(nullable=false)
	private LocalDateTime reserveDt;
	
	@Column(nullable=false)
	private String consDiv;
	
	@Lob
	@Column(nullable=false)
	private String consReqContent;
	
	@Column(nullable=false)
	private String reserveStatus;
	
	private LocalDateTime cancDt;
}
