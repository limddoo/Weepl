package com.weepl.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class CompCons {
	@Id
	@Column(name = "cons_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reserve_apply_cd")
	private ReserveApply reserveApply;

	private String name;

	private String status;

	private String msg;

	// 작성 일시
	@CreatedDate // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장한다.
	@Column(updatable = false)
	private LocalDateTime regDt;
}
