package com.weepl.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.controller.NoticeController;
import com.weepl.entity.BoardAttach;
import com.weepl.repository.BoardAttachRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardAttachService {
	private final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
	
	@Value("${boardAttachLocation}") // application.properties에 등록한 itemImgLocation값을 불러와서 변수 itemImgLocation에 넣는다.
	private String boardAttachLocation;
	
	private final BoardAttachRepository boardAttachRepository;
	private final FileService fileService;
	
	public void saveBoardAttach(BoardAttach boardAttach, MultipartFile boardAttachFile) throws Exception {
		String oriAttachName = boardAttachFile.getOriginalFilename();
		String attachName = "";
		String attachUrl = "";

		// 파일 업로드
		if (!StringUtils.isEmpty(oriAttachName)) {
			// 이미지를 등록했다면 저장할 바이트 수를 파라미터로 하는 uploadFile메소드를 호출한다.
			// 경로, 파일 이름, 파일
			attachName = fileService.uploadFile(boardAttachLocation, oriAttachName, boardAttachFile.getBytes()); 
			attachUrl = "/wee/boardAttach/" + attachName;																																														
		}
		// 첨부 정보 저장
		boardAttach.updateBoardAttach(oriAttachName, attachName, attachUrl); // 업로드했던 이미지 파일의 원래 이름, 실제 로컬에 저장된 이미지 파일의 이름,
		boardAttachRepository.save(boardAttach); // 업로드 결과 로컬에 저장된 이미지 파일을 불러오는 경로 등의 이미지 정보를 저장
	}

	public void updateBoardAttach(Long attachCd, MultipartFile boardAttachFile) throws Exception {
		if (!boardAttachFile.isEmpty()) { // 이미지를 수정한 경우 이미지를 업데이트한다.
			BoardAttach savedBoardAttach = boardAttachRepository.findById(attachCd) // 이미지 아이디를 이용하여 기존 저장했던 상품 이미지 엔티티를 조회한다.
					.orElseThrow(EntityNotFoundException::new);

			// 기존  파일 삭제
			if (!StringUtils.isEmpty(savedBoardAttach.getAttachName())) { // 기존에 등록된 이미지 파일이 있을 경우 해당 파일을 삭제한다
				fileService.deleteFile(boardAttachLocation + "/" + savedBoardAttach.getAttachName());
			}

			String oriAttachName = boardAttachFile.getOriginalFilename();
			String attachName = fileService.uploadFile(boardAttachLocation, oriAttachName, boardAttachFile.getBytes()); // 업데이트한 이미지
																											// 파일 업로드
			String attachUrl = "/wee/boardAttach/" + attachName;
			savedBoardAttach.updateBoardAttach(oriAttachName, attachName, attachUrl);
		}
	}

	public void deleteBoardAttach(Long noticeCd) throws Exception {
	
		List<BoardAttach> savedBoardAttach = boardAttachRepository.findByNotice_NoticeCdOrderByCdAsc(noticeCd);
		for(int i =0; i<savedBoardAttach.size(); i++) {
			BoardAttach boardAttach = savedBoardAttach.get(i);
			if(!boardAttach.getAttachName().isBlank()) {
			LOGGER.info("boardAttachLocation의 값 : {}",boardAttachLocation + "/" + boardAttach.getAttachName());
			fileService.deleteFile(boardAttachLocation + "/" + boardAttach.getAttachName());
			}
			else {
				break;
			}
		}
	}
	
}
