package com.weepl.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import com.weepl.entity.Member;
import com.weepl.entity.SweetBoard;
import com.weepl.entity.SweetComment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SweetCommentDto {

	// 고유번호
	private Long cd;

	// 댓글 내용
	@NotBlank(message="내용을 2자 이상 입력해주세요.")
	private String comment;

	// 작성일
	private LocalDateTime regDt;

	// 수정일
	private LocalDateTime modDt;
	
	// 삭제여부
	private String del_yn;

	// 회원정보
	private Member member;

	// 댓글 작성자
	private String createdBy;
	
	// 댓글 수정자
	private String modifiedBy;

	// 원본 댓글 고유번호
	private Long ori_cd;
	
	// 게시글정보
	private SweetBoard sweetBoard;

	private static ModelMapper modelMapper = new ModelMapper();

	public static SweetCommentDto of(SweetComment sweetComment) { // entity를 dto로
		return modelMapper.map(sweetComment, SweetCommentDto.class);
	}

	public SweetComment createSweetComment() {
		return modelMapper.map(this, SweetComment.class); // dto를 entity로
	}


}
