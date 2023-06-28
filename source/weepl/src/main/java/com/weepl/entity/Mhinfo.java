package com.weepl.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.weepl.constant.MhinfoCate;
import com.weepl.dto.MhinfoFormDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "mhinfo")
@Getter @Setter
@ToString
public class Mhinfo extends BaseEntity{
	@Id
	@Column(name = "mhinfo_cd")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cd;
	
	@Column(nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Column(columnDefinition = "integer default 0", nullable = false)
	private int view;
	
	@Column(columnDefinition = "integer default 0", nullable = false)
	private int likeCnt;
	
	public void updateMhinfo(MhinfoFormDto mhinfoFormDto) {
		this.title = mhinfoFormDto.getTitle();
		this.content = mhinfoFormDto.getContent();
	}
	
	@OneToMany(mappedBy = "mhinfo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<BoardImg> articleMhinfos = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private MhinfoCate mhinfoCate;

	public void setMhinfoCate(String mhinfoCate) {
	    this.mhinfoCate = MhinfoCate.valueOf(mhinfoCate);
	}
	
}
