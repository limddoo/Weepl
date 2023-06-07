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
import com.weepl.entity.BoardImg;
import com.weepl.repository.BoardImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardImgService {

	private final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
	
	@Value("${boardImgLocation}") // application.properties에 등록한 itemImgLocation값을 불러와서 변수 itemImgLocation에 넣는다.
	private String boardImgLocation;

	private final BoardImgRepository boardImgRepository;
	private final FileService fileService;

	public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception {
		String oriImgName = boardImgFile.getOriginalFilename();
		String imgName = "";
		String imgUrl = "";

		// 파일 업로드
		if (!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes()); // 이미지를 등록했다면 저장할
																										// 경로, 파일 이름, 파일
																										// 바이트 수를 파라미터로
																										// 하는
																										// uploadFile메소드를
																										// 호출한다.
			imgUrl = "/wee/boardImg/" + imgName;
		}

		// 상품 이미지 정보 저장
		boardImg.updateBoardImg(oriImgName, imgName, imgUrl); // 업로드했던 이미지 파일의 원래 이름, 실제 로컬에 저장된 이미지 파일의 이름,
		boardImgRepository.save(boardImg); // 업로드 결과 로컬에 저장된 이미지 파일을 불러오는 경로 등의 이미지 정보를 저장
	}

	public void updateBoardImg(Long imgCd, MultipartFile boardImgFile) throws Exception {
		if (!boardImgFile.isEmpty()) { // 이미지를 수정한 경우 이미지를 업데이트한다.
			BoardImg savedBoardImg = boardImgRepository.findById(imgCd) // 이미지 아이디를 이용하여 기존 저장했던 상품 이미지 엔티티를 조회한다.
					.orElseThrow(EntityNotFoundException::new);

			// 기존 이미지 파일 삭제
			if (!StringUtils.isEmpty(savedBoardImg.getImgName())) { // 기존에 등록된 이미지 파일이 있을 경우 해당 파일을 삭제한다
				fileService.deleteFile(boardImgLocation + "/" + savedBoardImg.getImgName());
			}

			String oriImgName = boardImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes()); // 업데이트한 이미지
																											// 파일 업로드
			String imgUrl = "/wee/boardImg/" + imgName;
			savedBoardImg.updateBoardImg(oriImgName, imgName, imgUrl);
		}
	}

	public void deleteBoardImg(Long noticeCd) throws Exception {
		LOGGER.info("deleteBoardImg 메소드 호출");
		LOGGER.info("noticeCd값은 : {}", noticeCd);
		List<BoardImg> savedBoardImg = boardImgRepository.findByNotice_NoticeCdOrderByCdAsc(noticeCd);
		LOGGER.info("savedBoardImg의 크기는 : {}",savedBoardImg.size());
		for(int i =0; i<savedBoardImg.size(); i++) {
			BoardImg boardImg = savedBoardImg.get(i);
			if(!boardImg.getImgName().isBlank()) {
			fileService.deleteFile(boardImgLocation + "/" + boardImg.getImgName());
			}
			else {
				break;
			}
		}
		

	}

}
