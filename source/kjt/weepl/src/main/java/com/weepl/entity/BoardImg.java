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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="board_img")
@Getter @Setter
public class BoardImg extends BaseEntity {
	@Id
	@Column(name="board_img_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;
	
	private String imgName; 
	private String oriImgName; 
	private String imgUrl; 
	private String repimgYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mhinfo_cd")
	private Mhinfo mhinfo;
	
	public void updateBoardImg(String oriImgName, String imgName, String imgUrl){
	this.oriImgName = oriImgName; 
	this.imgName = imgName;
	this.imgUrl = imgUrl;
	}
}
