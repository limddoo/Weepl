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

import com.weepl.dto.BoardConsFormDto;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="board_cons")
public class BoardCons extends BaseEntity{
	@Id
	@Column(name="board_cons_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;
	
	@Column(name="board_cons_pwd")
	private String pwd;
	
	private String title;
	
	private String content;
	
	private String del_yn;
	
	private String res_yn;
	
	
	public void updateCons(BoardConsFormDto boardConsFormDto) {
		this.title = boardConsFormDto.getTitle();
		this.content = boardConsFormDto.getContent();
		this.pwd = boardConsFormDto.getPwd();
	}
	
	

}
