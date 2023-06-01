package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.BoardAttachDto;
import com.weepl.dto.BoardImgDto;
import com.weepl.dto.SweetBoardDto;
import com.weepl.dto.SweetCommentDto;
import com.weepl.dto.SweetSearchDto;
import com.weepl.entity.BoardAttach;
import com.weepl.entity.BoardImg;
import com.weepl.entity.SweetBoard;
import com.weepl.entity.SweetComment;
import com.weepl.repository.BoardAttachRepository;
import com.weepl.repository.BoardImgRepository;
import com.weepl.repository.SweetBoardRepository;
import com.weepl.repository.SweetCommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SweetBoardService {

	private final SweetBoardRepository sweetBoardRepository;
	private final BoardImgService boardImgService;
	private final BoardImgRepository boardImgRepository;
	private final BoardAttachService boardAttachService;
	private final BoardAttachRepository boardAttachRepository;
	private final SweetCommentRepository sweetCommentRepository;

	// 게시글 저장
	public Long saveSweetBoard(SweetBoardDto sweetBoardDto, List<MultipartFile> boardImgFileList,
			List<MultipartFile> boardAttachFileList) throws Exception {
		SweetBoard sweetBoard = sweetBoardDto.createSweetBoard();
		sweetBoardRepository.save(sweetBoard);

		// 게시판 이미지 등록
		for (int i = 0; i < boardImgFileList.size(); i++) {
			BoardImg boardImg = new BoardImg();
			boardImg.setSweetBoard(sweetBoard);

			if (i == 0)
				boardImg.setRepImgYn("Y");
			else
				boardImg.setRepImgYn("N");
			boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
		}

		// 첨부파일 등록
		for (int i = 0; i < boardAttachFileList.size(); i++) {
			BoardAttach boardAttach = new BoardAttach();
			boardAttach.setSweetBoard(sweetBoard);
			boardAttachService.saveBoardAttach(boardAttach, boardAttachFileList.get(i));
		}
		return sweetBoard.getCd();
	}

	// 게시글 페이징
	@Transactional(readOnly = true)
	public Page<SweetBoard> getSweetBoardPage(SweetSearchDto sweetSearchDto, Pageable pageable) {
		return sweetBoardRepository.getSweetBoardPage(sweetSearchDto, pageable);
	}

	// 게시글 상세보기
	@Transactional(readOnly = true)
	public SweetBoardDto getSweetBoardDtl(Long cd) {
		
		// 해당 게시글 이미지 조회
		List<BoardImg> boardImgList = boardImgRepository.findBySweetBoardCdOrderByCdAsc(cd);
		List<BoardImgDto> boardImgDtoList = new ArrayList<>();

		for (BoardImg boardImg : boardImgList) {
			BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
			boardImgDtoList.add(boardImgDto);
		}
		
		// 해당 게시글 첨부파일 조회
		List<BoardAttach> boardAttachList = boardAttachRepository.findByCdOrderByCdAsc(cd);
		List<BoardAttachDto> boardAttachDtoList = new ArrayList<>();
		for (BoardAttach boardAttach : boardAttachList) {
			BoardAttachDto boardAttachDto = BoardAttachDto.of(boardAttach);
			boardAttachDtoList.add(boardAttachDto);
		}
		
		// 해당 게시글 댓글 조회
		List<SweetComment> sweetCommentList = sweetCommentRepository.findByCdOrderByCdAsc(cd);
		List<SweetCommentDto> sweetCommentDtoList = new ArrayList<>();
		
		for (SweetComment sweetComment : sweetCommentList) {
			SweetCommentDto sweetCommentDto = SweetCommentDto.of(sweetComment);
			sweetCommentDtoList.add(sweetCommentDto);
		}
		
		// 게시글 번호로 엔티티 조회 (미존재 시 예외처리)
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		SweetBoardDto sweetBoardDto = SweetBoardDto.of(sweetBoard);
		sweetBoardDto.setBoardImgDtoList(boardImgDtoList);
		sweetBoardDto.setBoardAttachDtoList(boardAttachDtoList);
		sweetBoardDto.setSweetCommentDtoList(sweetCommentDtoList);
		return sweetBoardDto;
	}

	// 게시판 수정
	public Long updateSweetBoard(SweetBoardDto sweetBoardDto, List<MultipartFile> boardImgFileList,
			List<MultipartFile> boardAttachFileList) throws Exception {
		// 게시글 수정
		SweetBoard sweetBoard = sweetBoardRepository.findById(sweetBoardDto.getCd())
				.orElseThrow(EntityNotFoundException::new);
		sweetBoard.updateSweetBoard(sweetBoardDto);

		// 이미지 수정
		List<Long> boardImgCds = sweetBoardDto.getBoardImgCds();
		for (int i = 0; i < boardImgFileList.size(); i++) {
			boardImgService.updateBoardImg(boardImgCds.get(i), boardImgFileList.get(i));
		}
		// 첨부파일 수정
		List<Long> boardAttachCds = sweetBoardDto.getBoardAttachCds();
		for (int i = 0; i < boardAttachFileList.size(); i++) {
			boardAttachService.updateBoardAttach(boardAttachCds.get(i), boardAttachFileList.get(i));
		}
		return sweetBoard.getCd();
	}
	

	// 게시글 삭제
	public void deleteSweetBoard(Long cd) {
		sweetBoardRepository.deleteById(cd);
	}
	
	// 좋아요 수 업데이트
	public Long addLike(Long cd, SweetBoardDto sweetBoardDto, int count) {
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd)
				.orElseThrow(EntityNotFoundException::new);
		sweetBoard.addLike(count);
		return sweetBoard.getCd();
	}

	// 댓글 추가
	@Transactional
	public void addComment(Long cd, SweetComment sweetComment) {
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
//		sweetComment.setMember(member);
		sweetComment.setSweetBoard(sweetBoard);
		sweetCommentRepository.save(sweetComment);
	}

	// 댓글 삭제
	@Transactional
	public void deleteComment(Long comCd) {
		sweetCommentRepository.deleteById(comCd);
	}
}
