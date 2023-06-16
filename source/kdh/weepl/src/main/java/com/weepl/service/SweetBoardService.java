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
import com.weepl.dto.SearchDto;
import com.weepl.dto.SweetBoardDto;
import com.weepl.entity.BoardAttach;
import com.weepl.entity.BoardImg;
import com.weepl.entity.Member;
import com.weepl.entity.SweetBoard;
import com.weepl.repository.BoardAttachRepository;
import com.weepl.repository.BoardImgRepository;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.SweetBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SweetBoardService {

	private final SweetBoardRepository sweetBoardRepository;
	private final MemberRepository memberRepository;

	private final BoardImgService boardImgService;
	private final BoardImgRepository boardImgRepository;

	private final BoardAttachService boardAttachService;
	private final BoardAttachRepository boardAttachRepository;

	// 게시글 저장
	public Long saveSweetBoard(String userId, SweetBoardDto sweetBoardDto, List<MultipartFile> boardImgFileList,
			List<MultipartFile> boardAttachFileList) throws Exception {
		Member member = memberRepository.findById(userId);
		SweetBoard sweetBoard = sweetBoardDto.createSweetBoard();
		sweetBoard.setMember(member);
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
			if(! boardAttachFileList.get(i).isEmpty()) {
				BoardAttach boardAttach = new BoardAttach();
				boardAttach.setSweetBoard(sweetBoard);
				boardAttachService.saveBoardAttach(boardAttach, boardAttachFileList.get(i));
			}
		}
		return sweetBoard.getCd();
	}

	// 게시글 페이징
	@Transactional(readOnly = true)
	public Page<SweetBoard> getSweetBoardPage(SearchDto sweetSearchDto, Pageable pageable) {
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
		List<BoardAttach> boardAttachList = boardAttachRepository.findBySweetBoardCdOrderByCdAsc(cd);
		List<BoardAttachDto> boardAttachDtoList = new ArrayList<>();
		for (BoardAttach boardAttach : boardAttachList) {
			BoardAttachDto boardAttachDto = BoardAttachDto.of(boardAttach);
			boardAttachDtoList.add(boardAttachDto);
		}
		// 게시글 번호로 엔티티 조회 (미존재 시 예외처리)
		SweetBoard sweetBoard = sweetBoardRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		SweetBoardDto sweetBoardDto = SweetBoardDto.of(sweetBoard);
		sweetBoardDto.setBoardImgDtoList(boardImgDtoList);
		sweetBoardDto.setBoardAttachDtoList(boardAttachDtoList);

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
		// 삭제하려는 첨부파일은 삭제
		List<Long> boardAttachCds = sweetBoardDto.getBoardAttachCds();
		for (int i = 0; i < boardAttachCds.size(); i++) {
			if(boardAttachCds.get(i) != null) {
				boardAttachService.updateBoardAttach(boardAttachCds.get(i));
			}
		}
		// 추가된 첨부파일 등록
		for (int i = 0; i < boardAttachFileList.size(); i++) {
			if(! boardAttachFileList.get(i).isEmpty()) {
				BoardAttach boardAttach = new BoardAttach();
				boardAttach.setSweetBoard(sweetBoard);
				boardAttachService.saveBoardAttach(boardAttach, boardAttachFileList.get(i));
			}
		}
		return sweetBoard.getCd();
	}

	// 게시글 삭제
	public void deleteSweetBoard(Long cd) {
		sweetBoardRepository.deleteById(cd);
	}

	// 첨부파일 다운로드
	public BoardAttachDto downloadBoardAttach(Long cd) {
		BoardAttach boardAttach = boardAttachRepository.findById(cd).orElseThrow(EntityNotFoundException::new);
		BoardAttachDto boardAttachDto = BoardAttachDto.of(boardAttach);
		return boardAttachDto;
	}

	// 좋아요 수 증가
	@Transactional
	public int addLike(Long cd) {
		return sweetBoardRepository.addLike(cd);
	}

	// 스윗 닉네임 존재 여부
	public String existSweetNickName(String id) {
		Member member = memberRepository.findById(id);
		if (member != null) {
			return member.getNickName();
		}

		return null;
	}
}
