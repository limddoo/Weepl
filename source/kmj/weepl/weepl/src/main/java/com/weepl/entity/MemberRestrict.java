package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.weepl.constant.RestrictStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member_restrict")
@Getter
@Setter
@ToString
public class MemberRestrict {
	@Id
	@Column(name = "restrict_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	@Column(name = "restrict_stdt")
	private LocalDateTime stdt;
	
	@Column(name = "restrict_eddt")
	private LocalDateTime eddt;
	
	@Column(name = "restrict_status")
	@Enumerated(EnumType.STRING)
	private RestrictStatus status;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;
}
