package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.BoardImgDto;
import com.weepl.dto.MhinfoFormDto;
import com.weepl.dto.MhinfoSearchDto;
import com.weepl.entity.BoardImg;
import com.weepl.entity.Mhinfo;
import com.weepl.repository.BoardImgRepository;
import com.weepl.repository.MhinfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MhinfoService {
	
	private final MhinfoRepository mhinfoRepository;
	private final BoardImgService boardImgService;
	private final BoardImgRepository boardImgRepository; 
	
	public Long findNextAvailableMhinfoCd() {
		List<Long> mhinfoCds = mhinfoRepository.findAllMhinfoCds();
		for (long i = 1; i <= mhinfoCds.size(); i++) {
			if(mhinfoCds.get((int) (i - 1)) != i){
				return Long.valueOf(i);
			}
		}
		return Long.valueOf(mhinfoCds.size() + 1);
	}
	
	public Long saveMhinfo(MhinfoFormDto mhinfoFormDto, List<MultipartFile> boardImgFileList) throws Exception {
		Mhinfo mhinfo = mhinfoFormDto.createMhinfo();
		mhinfoRepository.save(mhinfo);
		
		for(int i=0;i<boardImgFileList.size();i++) {
			BoardImg boardImg = new BoardImg();
			boardImg.setMhinfo(mhinfo);
			
			if(i == 0)
				boardImg.setRepImgYn("Y");
			else
				boardImg.setRepImgYn("N");
			boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
		}
		return mhinfo.getCd();
	}
	
	//새로운 게시물을 작성할 때 호출되는 메서드
	public void createMhinfo(Mhinfo mhinfo) {
		Long nextMhinfoCd = findNextAvailableMhinfoCd();
		mhinfo.setCd(nextMhinfoCd);
		// 게시물 저장 로직
		mhinfoRepository.save(mhinfo);
	}
	
	//게시물 삭제 메서드
	public void deleteMhinfo(Long mhinfoCd) {
		//게시물 삭제 로직
		mhinfoRepository.deleteById(mhinfoCd);
	}
	
	@Transactional(readOnly = true)
	public MhinfoFormDto getMhinfoDtl(Long mhinfoCd) {
		List<BoardImg> boardImgList = boardImgRepository.findByMhinfoCdOrderByCdAsc(mhinfoCd);
		List<BoardImgDto> boardImgDtoList = new ArrayList<>();
		for (BoardImg boardImg:boardImgList) {
			BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
			boardImgDtoList.add(boardImgDto);
		}
		
		Mhinfo mhinfo = mhinfoRepository.findById(mhinfoCd)
				.orElseThrow(EntityNotFoundException::new);
		MhinfoFormDto mhinfoFormDto = MhinfoFormDto.of(mhinfo);
		mhinfoFormDto.setBoardImgDtoList(boardImgDtoList);
		return mhinfoFormDto;
	}
	
	public Long updateMhinfo(MhinfoFormDto mhinfoFormDto, List<MultipartFile> boardImgFileList) throws Exception {
	    Mhinfo mhinfo = mhinfoRepository.findById(mhinfoFormDto.getCd())
	            .orElseThrow(EntityNotFoundException::new);
	    mhinfo.updateMhinfo(mhinfoFormDto);
	    
	    mhinfo.setMhinfoCate(mhinfoFormDto.getMhinfoCate()); // mhinfoCate 값을 설정해줍니다.
	    
	    List<Long> boardImgCds = mhinfoFormDto.getBoardImgCds();
	    for(int i=0; i<boardImgFileList.size(); i++) {
	        boardImgService.updateBoardImg(boardImgCds.get(i), boardImgFileList.get(i));
	    }
	    return mhinfo.getCd();
	}
	
	@Transactional(readOnly = true)
    public Page<Mhinfo> getMhinfoPage(MhinfoSearchDto mhinfoSearchDto, Pageable pageable){
    	return mhinfoRepository.getMhinfoPage(mhinfoSearchDto, pageable);
    }
	
	@Transactional
	public int updateView(Long mhinfoCd) {
		return mhinfoRepository.updateView(mhinfoCd);
	}
	
	@Transactional
	public int updateLikes(Long mhinfoCd) {
		return mhinfoRepository.updateLikes(mhinfoCd);
	}

//	@Transactional(readOnly = true)
//    public Page<MainMhinfoDto> getMainMhinfoPage(MhinfoSearchDto mhinfoSearchDto, Pageable pageable){
//    	return mhinfoRepository.getMainMhinfoPage(mhinfoSearchDto, pageable);
//    }

//	public void deleteMhinfo(Long cd) {
//		Mhinfo mhinfo = mhinfoRepository.findById(cd)
//				.orElseThrow(EntityNotFoundException::new);
//		mhinfoRepository.delete(mhinfo);
//	}
	
	
}
