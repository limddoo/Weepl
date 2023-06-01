package com.weepl.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.weepl.dto.NoticeFormDto;

import lombok.Data;

@Entity
@Table(name = "notice")
@Data
public class Notice extends BaseEntity{

	@Id
	@Column(name="notice_cd")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long noticeCd; //공지사항 고유번호
	
	@Column(nullable=false, length=200)
	private String title; //제목
	
	@Lob
	@Column(nullable=false)
	private String content; //내용
	
	@OneToMany(mappedBy="notice", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<BoardImg> boardImg = new ArrayList<>();
	
	@OneToMany(mappedBy="notice", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<BoardAttach> boardAttach = new ArrayList<>();
	
	public static Notice createNotice(NoticeFormDto noticeFormDto) {
		Notice notice = new Notice();
		notice.setTitle(noticeFormDto.getTitle());
		notice.setContent(noticeFormDto.getContent());
		notice.setRegDt(LocalDateTime.now());
		notice.setModDt(LocalDateTime.now());
		return notice;
	}
	
	public void updateNotice(NoticeFormDto noticeFormDto) {
		this.noticeCd = noticeFormDto.getNoticeCd();
		this.title = noticeFormDto.getTitle();
		this.content = noticeFormDto.getContent();
	}
}

