package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.SweetCommentDto;
import com.weepl.entity.SweetBoard;
import com.weepl.entity.SweetComment;
import com.weepl.repository.SweetBoardRepository;
import com.weepl.repository.SweetCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SweetCommentService {

	private final SweetCommentRepository sweetCommentRepository;
	private final SweetBoardRepository sweetBoardRepository;

	// 댓글 조회
	@Transactional
	public SweetCommentDto getSweetComment(Long cd) {
		// 해당 게시글 댓글 조회
		List<SweetComment> sweetCommentList = sweetCommentRepository.findByCdOrderByCdAsc(cd);
		List<SweetCommentDto> sweetCommentDtoList = new ArrayList<>();

		for (SweetComment sweetComment : sweetCommentList) {
			SweetCommentDto sweetCommentDto = SweetCommentDto.of(sweetComment);
			sweetCommentDtoList.add(sweetCommentDto);
		}
		SweetComment sweetComment = sweetCommentRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		SweetCommentDto sweetCommentDto = SweetCommentDto.of(sweetComment);
		return sweetCommentDto;
	}
	
	// 댓글 저장
	@Transactional
	public Long saveComment(Long cd, SweetCommentDto sweetCommentDto) {
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd)
				.orElseThrow(EntityNotFoundException::new);
		sweetCommentDto.setCd(cd);
		sweetCommentDto.setSweetBoard(sweetBoard);
		
		SweetComment sweetComment = sweetCommentDto.createSweetComment();
		sweetCommentRepository.save(sweetComment);
		
		return sweetCommentDto.getCd();
	}
	
	// 댓글 등록
	@Transactional
	public void addComment(Long cd, SweetComment sweetComment) {
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		sweetComment.setSweetBoard(sweetBoard);
		sweetCommentRepository.save(sweetComment);
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long cd) {
		SweetComment sweetComment = sweetCommentRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		sweetCommentRepository.delete(sweetComment);
	}
}