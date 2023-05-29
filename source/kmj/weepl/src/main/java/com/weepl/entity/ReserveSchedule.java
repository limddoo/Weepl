package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
//캘린더의 예약 가능 날짜와 불가 날짜 등록용
@Entity
@Table(name="reserveschedule")
@Data
public class ReserveSchedule {
	
	@Id
	@Column(name="reserve_schedule_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long reserveScheduleCd;
	
	private LocalDateTime reserveDt;
	
	private String reserveTime;
}
