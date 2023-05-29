package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class ReserveSchedule {
	@Id
	@Column(name="reserve_schedule_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private LocalDateTime reserveDt;
	
	private String reserveTime;
}
