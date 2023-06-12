package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.SweetCommentDto;
import com.weepl.entity.Member;
import com.weepl.entity.SweetBoard;
import com.weepl.entity.SweetComment;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.SweetBoardRepository;
import com.weepl.repository.SweetCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SweetCommentService {
	
	private final MemberRepository memberRepository;
	
	private final SweetCommentRepository sweetCommentRepository;
	
	private final SweetBoardRepository sweetBoardRepository;

	// 댓글 조회
	public List<SweetCommentDto> getSweetComment(Long cd) {
		// 해당 게시글 댓글
		List<SweetComment> sweetComments = sweetCommentRepository.findBySweetBoardCdOrderByCdDesc(cd);
		List<SweetCommentDto> sweetCommentDtoList = new ArrayList<>();
		
		for (SweetComment sweetComment : sweetComments) {
			SweetCommentDto sweetCommentDto = SweetCommentDto.of(sweetComment);
			sweetCommentDtoList.add(sweetCommentDto);
		}
		return sweetCommentDtoList;
	}
	
	// 댓글 저장
	public void saveComment(String userId, Long cd, String content) {

		SweetComment sweetComment = new SweetComment();
		Member member = memberRepository.findById(userId);
		SweetBoard sweetBoard = sweetBoardRepository.findByCd(cd);
		sweetComment.setMember(member);
		sweetComment.setComment(content);
		sweetComment.setSweetBoard(sweetBoard);
		sweetComment.setDel_yn("N");
		sweetCommentRepository.save(sweetComment);
	}
	
	// 댓글 삭제
	@Transactional
	public void deleteComment(Long cd) {
		SweetComment sweetComment = sweetCommentRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		sweetComment.setDel_yn("Y");
		sweetCommentRepository.delete(sweetComment);
	}
}