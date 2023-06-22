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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="board_attach")
@Data
public class BoardAttach extends BaseEntity{

	@Id
	@Column(name="attach_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long cd;
	
	private String attachName;
	private String oriAttachName;
	private String attachUrl;
	private String boardDiv;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="notice_cd")
	private Notice notice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="sweet_board_cd")
	@OnDelete(action=OnDeleteAction.CASCADE)
	private SweetBoard sweetBoard;

	public void updateBoardAttach(String oriAttachName, String attachName, String attachUrl) {
		this.oriAttachName = oriAttachName;
		this.attachName = attachName;
		this.attachUrl = attachUrl;
		
	}

	public void setCd(SweetBoard sweetBoard) {
		this.sweetBoard = sweetBoard;
	}
}
