package com.weepl.entity;

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
@Table(name="compcons")
@Data
public class CompCons {
	
	@Id
	@Column(name="cons_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long consCd;
	
	@ManyToOne(fetch=FetchType.LAZY)  //회원 고유번호
	@JoinColumn(name="reserve_apply_cd")
	private ReserveApply  reserveApply;
	
	@Lob
	@Column(nullable=false)
	private String consContent;
}
