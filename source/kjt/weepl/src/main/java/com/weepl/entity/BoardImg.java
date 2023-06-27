package com.weepl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="board_img")
@Data
public class BoardImg extends BaseEntity{

	@Id
	@Column(name="board_img_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private String imgName;
	private String oriImgName;
	private String imgUrl;
	private String repImgYn;
	private String boardDiv;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mhinfo_cd")
	private Mhinfo mhinfo;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="notice_cd")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private Notice notice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sweet_board_cd")
	@OnDelete(action= OnDeleteAction.CASCADE)
	private SweetBoard sweetBoard;
	
	public void updateBoardImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
