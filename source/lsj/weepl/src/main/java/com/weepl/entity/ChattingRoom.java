package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "chatting_room")
@Getter
@Setter
@ToString
public class ChattingRoom {
	@Id
	@Column(name="cr_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;
	
	// ReserveApply의 reserveApplyCd
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private ReserveApply reserveApply;
	
	public static ChattingRoom createChattingRoom(ReserveApply reserveApply) {
		ChattingRoom cr = new ChattingRoom();
		cr.member = reserveApply.getMemberCd();
		cr.reserveApply = reserveApply;
		
		return cr;
	}
}
