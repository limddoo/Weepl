package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.weepl.dto.ReserveApplyDto;

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
	
	private String reserveDate;
	
	private String reserveTime;
	
	private String status;
	
	public static ReserveSchedule createReserveSchedule(String reserveDate, String reserveTime) {
		ReserveSchedule rs = new ReserveSchedule();
		rs.setReserveDate(reserveDate);
		rs.setReserveTime(reserveTime);
		rs.setStatus("예약가능");	
		return rs;
	}
	public void updateReserveSchedule(ReserveApplyDto reserveApplyDto) {
		this.cd = reserveApplyDto.getReserveScheduleCd();
		this.status = "예약완료";
	}
}
