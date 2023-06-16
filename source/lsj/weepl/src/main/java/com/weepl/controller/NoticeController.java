package com.weepl.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.NoticeFormDto;
import com.weepl.dto.SearchDto;
import com.weepl.entity.Notice;
import com.weepl.service.NoticeService;

@RequestMapping("/board")
@Controller
public class NoticeController {

	private final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
	
	@Autowired
	NoticeService noticeService;
	
	
	//공지사항 리스트
	@GetMapping(value= {"/notice","/notice/{page}"}) //페이지 번호가 없는 경우와 있는 경우 2가지 매핑
	public String manageNotice(SearchDto noticeSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		
		//PageRequest.of 메소드를 통해 Pageable객체 생성. 해당 페이지 조회, 페이지 번호가 없으면 0페이지에서 3개 조회
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10); 
		
		//조회조건과 페이징 정보를 파라미터로 넘겨 Page<Item> 객체를 반환받는다.
		Page<Notice> notices = noticeService.getAdminNoticePage(noticeSearchDto, pageable);
		
		model.addAttribute("notices", notices); //조회한 상품 데이터와 페이징 정보를 뷰에 전달한다
		model.addAttribute("noticeSearchDto", noticeSearchDto); //페이지 전환 시 기존 검색조건을 유지한채 이동할 수 있도록 뷰에 다시 전달
		model.addAttribute("maxPage", 5); //상품 관리 하단에 보여줄 페이지 번호의 최대 개수이다
		
		return "notice/notice";
	}
	
	
	//관리자 상세보기, url경로 변수는 {}로 표현한다.
	@GetMapping(value="/admin/noticedtl/{noticeCd}") 
	public String noticeDtlAdmin(@PathVariable("noticeCd") Long noticeCd, Model model) throws Exception{
		 try {
			NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeCd); //조회한 상품 데이터를 모델에 담아 뷰로 전달한다.			LOGGER.info("getNoticeDtl 메서드가 호출되었습니다.");
			model.addAttribute("noticeFormDto", noticeFormDto);
		} catch (EntityNotFoundException e) { //상품 엔티티가 존재하지 않을경우 에러메시지를 담아 상품 등록 페이지로 이동한다.
			model.addAttribute("errorMessage", "존재하지 않는 글입니다.");
			model.addAttribute("noticeFormDto", new NoticeFormDto());
			return "board/notice/noticeForm";
		}	 	 
		 return "notice/noticeForm";
	}
	
	
	//사용자 상세보기
	@GetMapping(value="/noticedtl/{noticeCd}")
	public String noticeDtl(Model model, @PathVariable("noticeCd") Long noticeCd) {
		NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeCd);
		model.addAttribute("noticeFormDto", noticeFormDto);
		return "notice/noticeDtl";
	}
	
	
	//공지사항 등록 폼 띄우기
	@GetMapping(value="/notice/new") 
	public String noticeForm(Model model) {
		model.addAttribute("noticeFormDto", new NoticeFormDto());
		return "notice/noticeForm";
	}
	
	
	 //공지사항 등록
	@PostMapping(value = "/notice/new")
	public String createNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, Model model, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, @RequestParam("boardAttachFile") List<MultipartFile> boardAttachFileList) throws Exception {
		
		LOGGER.info("++++++++++++++++++{}",noticeFormDto);
		
		
		if (bindingResult.hasErrors()) {  //필수값이 없다면 다시 공지사항 등록 페이지로 이동
			return "board/notice/noticeForm";
		}
		
		try {
			noticeService.saveNotice(noticeFormDto, boardImgFileList, boardAttachFileList); //공지사항 저장 로직을 호출. 공지사항과 이미지 정보,첨부파일을 넘긴다
		} 
		catch(Exception e) {
			model.addAttribute("errorMessage", "공지사항 등록중 에러가 발생하였습니다.");
			e.printStackTrace();
			return "notice/noticeForm";
		}
		return "redirect:/board/notice";
	}
	
	
	//첨부파일 다운로드
	@SuppressWarnings("rawtypes")
	@RequestMapping(value={"/notice/download/{attachCd}", "/notice/download/"})
	public ResponseEntity downloadAttach(@PathVariable("attachCd") Long attachCd, @RequestHeader("User-Agent") String userAgent) throws IOException {
	
		StringBuilder filePath = new StringBuilder("D:");
		filePath.append(noticeService.downloadNoticeAttach(attachCd).getAttachUrl());
		// 한글파일 깨짐방지
		String downloadName = encodeFileName(noticeService.downloadNoticeAttach(attachCd).getOriAttachName(), userAgent);
		
		StringBuilder fileName = new StringBuilder(downloadName);
		
		try {
		InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));
		//model.addAttribute("notice", noticeService.downloadNoticeAttach(attachCd));
		return ResponseEntity.ok()
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .cacheControl(CacheControl.noCache())
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
	            .body(resource);}
		catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.OK);
	}
	
	
	//공지사항 수정
	@PostMapping(value="/notice/{noticeCd}")
	public String updateNotice(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult, @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, @RequestParam("boardAttachFile") List<MultipartFile> boardAttachFileList, Model model)throws Exception {
		if(bindingResult.hasErrors()) {
			return "notice/noticeForm";
		}
		
		try {
			System.out.println("추가될 이미지 리스트: "+boardImgFileList);
			System.out.println("수정될 이미지 리스트: "+noticeFormDto.getBoardImgCds());
			noticeService.updateNotice(noticeFormDto, boardImgFileList, boardAttachFileList);
		}
		catch(Exception e) {
			model.addAttribute("errorMessage", "글 수정 중 에러가 발생했습니다.");
			e.printStackTrace();
			return "notice/noticeForm";
		}	
		return "redirect:/board/notice";
	}
	
	
	//공지사항 삭제
	@GetMapping(value="/notice/delete/{noticeCd}")
	public String deleteNotice(@PathVariable("noticeCd") Long noticeCd) throws Exception {
		try {
			noticeService.deleteNotice(noticeCd);
			return "redirect:/board/notice";
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "redirect:/board/notice";
	}
	
	// 한글파일 깨짐방지 파일명 인코딩
	private String encodeFileName(String fileName, String userAgent) throws IOException {
		String downloadName = null;
		if(userAgent.contains("Trident")) {
			downloadName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if(userAgent.contains("Edge")) {
			downloadName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			downloadName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
		}
		return downloadName;
	}
}
