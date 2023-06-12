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

import com.weepl.dto.SweetBoardDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "SWEETBOARD")
public class SweetBoard extends BaseEntity {
	
	@Id
	@Column(name = "sweet_board_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;	// 스윗게시판 고유번호(기본키)
	
	// 게시판 제목
	@Column(nullable = false)
	private String title;
	
	// 게시판 내용
	@Lob
	@Column(nullable = false)
	private String content;
	
	// 게시판 구분(학교 업무, 상담 전문성, 서식, 자유)
	@Column(nullable = false)
	private String board_div;
	
	// 좋아요 개수 누적
	@Column(nullable = false)
	private int like_cnt;
	
	// 삭제 여부
	@Column(nullable = false)
	private String del_yn;

	// 회원정보
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="mem_cd")
	private Member member;
	
	public static SweetBoard createSweetBoard(SweetBoardDto sweetBoardDto) {
		SweetBoard sweetBoard = new SweetBoard();
		sweetBoard.setTitle(sweetBoardDto.getTitle());
		sweetBoard.setContent(sweetBoardDto.getContent());
		sweetBoard.setBoard_div(sweetBoardDto.getBoard_div());
		sweetBoard.setLike_cnt(0);
		sweetBoard.setDel_yn("N");
		return sweetBoard;
	}
	
	public void updateSweetBoard(SweetBoardDto sweetBoardDto) {
		this.title = sweetBoardDto.getTitle();
		this.content = sweetBoardDto.getContent();
		this.board_div = sweetBoardDto.getBoard_div();
		this.like_cnt = sweetBoardDto.getLike_cnt();
		this.del_yn = sweetBoardDto.getDel_yn();
	}
	
//	// 좋아요 개수 증가
//	public void addLike() {
//		this.like_cnt += 1;
//	}	
	public void addLike(int count) {
		this.like_cnt += count;
	}
}
