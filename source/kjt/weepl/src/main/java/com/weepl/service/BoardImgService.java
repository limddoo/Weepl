package com.weepl.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.weepl.dto.BoardImgDto;
import com.weepl.entity.BoardImg;
import com.weepl.repository.BoardImgRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardImgService {

	@Value("${boardImgLocation}")
	private String boardImgLocation;
	
	private final BoardImgRepository boardImgRepository;
	private final FileService fileService;
	
	public void saveBoardImg(BoardImg boardImg, MultipartFile boardImgFile) throws Exception {
		String oriImgName = boardImgFile.getOriginalFilename();
		String imgName= "";
		String imgUrl = "";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes());
			imgUrl = "/images/boardImg/" + imgName;
		}
		
		boardImg.updateBoardImg(oriImgName, imgName, imgUrl);
		boardImgRepository.save(boardImg);
	}
	
	public void updateBoardImg(Long boardImgCd, MultipartFile boardImgFile) throws Exception {
		if(!boardImgFile.isEmpty()) {
			BoardImg savedBoardImg = boardImgRepository.findById(boardImgCd)
					.orElseThrow(EntityNotFoundException::new);
		
			if(!StringUtils.isEmpty(savedBoardImg.getImgName())) {
				fileService.deleteFile(boardImgLocation+"/"+savedBoardImg.getImgName());
			}
			
			String oriImgName = boardImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(boardImgLocation, oriImgName, boardImgFile.getBytes());
			String imgUrl = "/images/boardImg/" + imgName;
			savedBoardImg.updateBoardImg(oriImgName, imgName, imgUrl);
		}
	}

		public void deleteBoardImg(Long mhinfoCd) throws Exception{
			List<BoardImgDto> boardImgLists = boardImgRepository.getLists(mhinfoCd);
								
			for(int i = 0; i <boardImgLists.size() ; i++) {
				String boardImg = boardImgLists.get(i).getImgName();
				fileService.deleteFile(boardImgLocation + "/" + boardImg);
			}
		//리스트로 받아온걸 변수 하나에 넣어줘야 됨 *for문으로
		//for -> 리스트로 받아온 데이터를 하나씩 변수에 넣고, 해당 변수를 fileService.deleteFile 한다
		
	}
}
