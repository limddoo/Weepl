package com.weepl.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.weepl.entity.Member;
import com.weepl.entity.SweetBoard;
import com.weepl.entity.SweetComment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SweetBoardDto {
	
	// 게시글 순번
	private Long cd;
	
	// 게시글 제목
	@NotBlank(message="제목을 2자 이상 입력해주세요.")
	private String title;
	
	// 게시글 내용
	@NotBlank(message="내용을 2자 이상 입력해주세요.")
	private String content;
	
	// 게시판 구분
	private String board_div;
	
	// 좋아요 개수 카운트
	private int like_cnt = 0;
	
	// 삭제 여부 (Y 또는 N 으로 지정)
	private String del_yn = "N";
	
	// 게시글 작성자
	private String createdBy;
	
	// 게시글 등록일자
	private LocalDateTime regDt;
	
	// 회원번호
	private Long mem_cd;
	
	// 회원정보
	private Member member;
	
	// 이미지를 저장하는 리스트
	private List<BoardImgDto> boardImgDtoList = new ArrayList<>();
	
	// 이미지 번호를 저장하는 리스트
	private List<Long> boardImgCds = new ArrayList<>();
	
	// 첨부파일 저장하는 리스트
	private List<BoardAttachDto> boardAttachDtoList = new ArrayList<>();
	
	// 첨부파일 번호를 저장하는 리스트
	private List<Long> boardAttachCds = new ArrayList<>();
	
	// 댓글 번호를 저장하는 리스트
	private List<Long> sweetCommentCds = new ArrayList<>();	
	
	// 댓글을 저장하는 리스트
	@OrderBy("cd desc")
	@OneToMany(mappedBy = "sweetBoard", fetch = FetchType.EAGER)
	private List<SweetComment> sweetCommentList = new ArrayList<>();
	
	public SweetBoardDto() {
		super();
	}
	
	public SweetBoardDto(String title, String content, String board_div, int like_cnt) {
		super();
		this.title = title;
		this.content = content;
		this.board_div = board_div;
		this.like_cnt = like_cnt;
	}
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public SweetBoard createSweetBoard() {
		return modelMapper.map(this, SweetBoard.class);		// dto를 entity로
	}
	
	public static SweetBoardDto of(SweetBoard sweetBoard) {
		return modelMapper.map(sweetBoard, SweetBoardDto.class);	// entity를 dto로
	}
}
