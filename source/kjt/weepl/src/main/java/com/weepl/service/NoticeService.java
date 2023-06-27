package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.BoardAttachDto;
import com.weepl.dto.BoardImgDto;
import com.weepl.dto.NoticeFormDto;
import com.weepl.dto.SearchDto;
import com.weepl.entity.BoardAttach;
import com.weepl.entity.BoardImg;
import com.weepl.entity.Notice;
import com.weepl.repository.BoardAttachRepository;
import com.weepl.repository.BoardImgRepository;
import com.weepl.repository.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeRepository noticeRepository;
	private final BoardImgService boardImgService;
	private final BoardImgRepository boardImgRepository;
	private final BoardAttachService boardAttachService;
	private final BoardAttachRepository boardAttachRepository;
	private final Logger LOGGER = LoggerFactory.getLogger(NoticeService.class);
	
	@Transactional(readOnly = true)
	public Page<Notice> getNotices(SearchDto noticeSearchDto, Pageable pageable){
		return noticeRepository.getAllNotices(noticeSearchDto, pageable);
	}
	
	
	//공지등록
	public Long saveNotice(NoticeFormDto noticeFormDto, List<MultipartFile> boardImgFileList, List<MultipartFile> boardAttachFileList) throws Exception {
		Notice notice  = noticeFormDto.noticeFormDtoToNotice(); //공지사항 등록 폼으로부터 입력 받은 데이터를 이용하여 notice 객체를 생성		
		noticeRepository.save(notice); //공지사항 데이터를 저장
		
		//이미지 등록
		for(int i = 0; i<boardImgFileList.size(); i++) {
			BoardImg boardImg = new BoardImg();
			boardImg.setNotice(notice);
			
			if(i==0) //첫번째 이미지일 경우 대표 이미지 여부 값을 Y로 세팅한다. 나머지 이미지는 N으로 설정한다.
				boardImg.setRepImgYn("Y");
			else
				boardImg.setRepImgYn("N");
			
			boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i)); //이미지 정보를 저장한다.
		}	
		
		//첨부파일 등록
		for (int i = 0; i < boardAttachFileList.size(); i++) {
			if (!boardAttachFileList.get(i).isEmpty()) {
				BoardAttach boardAttach = new BoardAttach();
				boardAttach.setNotice(notice);
				boardAttachService.saveBoardAttach(boardAttach, boardAttachFileList.get(i)); // 이미지 정보를 저장한다.
			}
		}
		return notice.getCd();
	}

	
	//공지사항 상세정보
	@Transactional(readOnly=true)
	public NoticeFormDto getNoticeDtl(Long noticeCd) {
		
		//해당 공지사항 이미지 조회
		List<BoardImg> boardImgList = boardImgRepository.findByNotice_CdOrderByCdAsc(noticeCd); 
		List<BoardImgDto> noticeImgDtoList = new ArrayList<>();
		for(BoardImg boardImg : boardImgList) { //조회한 BoardImg 엔티티를 NoticeImgDto 객체로 만들어서 리스트에 추가한다.
			BoardImgDto noticeImgDto = BoardImgDto.of(boardImg);
			noticeImgDtoList.add(noticeImgDto);
		}
		
		//해당 공지사항 이미지 조회
		List<BoardAttach> boardAttachList = boardAttachRepository.findByNotice_CdOrderByCdAsc(noticeCd); 
		List<BoardAttachDto> noticeAttachDtoList = new ArrayList<>();
		for(BoardAttach boardAttach : boardAttachList) { //조회한 BoardImg 엔티티를 NoticeImgDto 객체로 만들어서 리스트에 추가한다.
			BoardAttachDto noticeAttachDto = BoardAttachDto.of(boardAttach);
			noticeAttachDtoList.add(noticeAttachDto);
		}
		
		//공지사항 아이디를 통해 공지사항 엔티티를 조회한다. 존재하지 않을땐 예외를 발생시킨다.
		Notice notice = noticeRepository.findById(noticeCd)   
				.orElseThrow(EntityNotFoundException::new);
		NoticeFormDto noticeFormDto = NoticeFormDto.noticeToNoticeFormDto(notice);
		noticeFormDto.setBoardImgDtoList(noticeImgDtoList);
		noticeFormDto.setBoardAttachDtoList(noticeAttachDtoList);
		return noticeFormDto;
	}	
	
	
	//공지사항 수정
	public Long updateNotice(NoticeFormDto noticeFormDto, List<MultipartFile> boardImgFileList, List<MultipartFile> boardAttachFileList) throws Exception{
		//글 수정
		Notice notice = noticeRepository.findById(noticeFormDto.getCd()) //상품 등록 화면으로 전달받은 상품 아이디를 이용 상품엔티티 조회
				.orElseThrow(EntityNotFoundException::new);
		notice.updateNotice(noticeFormDto); // 공지 등록화면으로 전달 받은 NoticeFormDto를 통해 엔티티 업데이트
		
		//이미지 수정
		List<Long> noticeImgIds = noticeFormDto.getBoardImgCds(); //이미지 아이디 리스트 반환
		for(int i = 0; i<noticeImgIds.size(); i++) {
			boardImgService.updateBoardImg(noticeImgIds.get(i), boardImgFileList.get(i)); //상품 이미지 아이디를 업데이트하기 위해서 상품 이미지 아이디, 상품 이미지 파일 정보 전달
		}
		
		// 삭제하려는 첨부파일은 삭제
		List<Long> noticeAttachIds = noticeFormDto.getBoardAttachCds(); //이미지 아이디 리스트 반환
		for(int i = 0; i<noticeAttachIds.size(); i++) {
			if(noticeAttachIds.get(i) != null) {
				boardAttachService.updateBoardAttach(noticeAttachIds.get(i));
			}
		}
		
		// 추가된 첨부파일 등록
		for (int i = 0; i < boardAttachFileList.size(); i++) {
			if (!boardAttachFileList.get(i).isEmpty()) {
				BoardAttach boardAttach = new BoardAttach();
				boardAttach.setNotice(notice);
				boardAttachService.saveBoardAttach(boardAttach, boardAttachFileList.get(i));
			}
		}
		return notice.getCd();
	}
	
	public List<Notice> getNoticeList() {
		return noticeRepository.findAll();
	}
	
	//공지사항 삭제
	public void deleteNotice(Long noticeCd) throws Exception {	
		//공지사항 이미지 삭제
		boardImgService.deleteBoardImg(noticeCd);
		
		//공지사항 첨부파일 삭제
		boardAttachService.deleteBoardAttach(noticeCd);
		
		//공지사항 삭제
		noticeRepository.deleteById(noticeCd);	
	}
	
	
	//첨부파일 다운로드
	public BoardAttachDto downloadNoticeAttach(Long attachCd) {
		BoardAttach boardAttach = boardAttachRepository.findById(attachCd)
				.orElseThrow(EntityNotFoundException::new);
		
		BoardAttachDto noticeAttachDto = BoardAttachDto.of(boardAttach);
		return noticeAttachDto;
	}
	
}
