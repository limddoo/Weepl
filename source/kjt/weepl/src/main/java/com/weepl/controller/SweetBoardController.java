package com.weepl.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.SearchDto;
import com.weepl.dto.SweetBoardDto;
import com.weepl.dto.SweetCommentDto;
import com.weepl.entity.SweetBoard;
import com.weepl.service.SweetBoardService;
import com.weepl.service.SweetCommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/sweetboard")
public class SweetBoardController {

	private final SweetBoardService sweetBoardService;
	private final SweetCommentService sweetCommentService;

	// 스윗게시판 메인페이지
	@GetMapping(value = { "/list", "/list/{page}" })
	public String sweetBoardManage(SearchDto sweetSearchDto, @PathVariable("page") Optional<Integer> page,
			Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<SweetBoard> sweetBoards = sweetBoardService.getSweetBoardPage(sweetSearchDto, pageable);

		model.addAttribute("sweetBoards", sweetBoards);
		model.addAttribute("sweetSearchDto", sweetSearchDto);
		model.addAttribute("maxPage", 5);

		return "sweetboard/sweetList";
	}
	
	// 게시판 리스트 돌아가기
	@PostMapping(value = "/list")
	public String sweetBoard(Model model) {
		return "redirect:/sweetboard/list";
	}

	// 게시글 등록하는 화면
	@GetMapping(value = "/add")
	public String sweetBoardForm(Model model) {
		model.addAttribute("sweetBoardDto", new SweetBoardDto());
		return "sweetboard/sweetForm";
	}

	// 게시글을 새로 등록
	@PostMapping(value = "/add")
	public String addSweetBoard(@Valid SweetBoardDto sweetBoardDto, BindingResult bindingResult, Model model, Principal principal,
			@RequestParam("boardImgFile") List<MultipartFile> boardImgFileList,
			@RequestParam("boardAttachFile") List<MultipartFile> boardAttachFileList) {
		
		String userId = principal.getName();
		if (bindingResult.hasErrors()) {
			return "sweetboard/sweetForm";
		}
		try {
			sweetBoardService.saveSweetBoard(userId, sweetBoardDto, boardImgFileList, boardAttachFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글을 저장하는 과정에서 에러가 발생하였습니다.");
			return "sweetboard/sweetForm";
		}
		return "redirect:/sweetboard/list";
	}

	// 게시글 상세보기 화면
	@GetMapping(value = "/dtl/{cd}")
	public String sweetBoardDtl(Model model, @PathVariable("cd") Long cd) {
		SweetBoardDto sweetBoardDto = sweetBoardService.getSweetBoardDtl(cd);
		List<SweetCommentDto> sweetCommentDtoList = sweetCommentService.getSweetComment(cd);
		
		model.addAttribute("sweetBoardDto", sweetBoardDto);
		model.addAttribute("sweetCommentDtoList", sweetCommentDtoList);
		
		return "sweetboard/sweetDtl";
	}
	
	// 게시글 수정하는 화면
	@GetMapping(value = "/mod/{cd}")
	public String sweetBoardModForm(@PathVariable("cd") Long cd, Model model) {
		try {
			SweetBoardDto sweetBoardDto = sweetBoardService.getSweetBoardDtl(cd);
			model.addAttribute("sweetBoardDto", sweetBoardDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 게시글입니다.");
			model.addAttribute("sweetBoardDto", new SweetBoardDto());
			return "sweetBoard/sweetForm";
		}
		return "sweetboard/sweetForm";
	}
	
	// 첨부파일 다운로드
	@SuppressWarnings("rawtypes")
	@GetMapping(value="/dtl/download/{cd}")
	public ResponseEntity attachDownload(@PathVariable("cd") Long cd, Model model, @RequestHeader("User-Agent") String userAgent) throws IOException {
		StringBuilder filePath = new StringBuilder("D:");
		filePath.append(sweetBoardService.downloadBoardAttach(cd).getAttachUrl());
		// 한글파일 깨짐방지
		String downloadName = encodeFileName(sweetBoardService.downloadBoardAttach(cd).getOriAttachName(), userAgent);
		
		StringBuilder fileName = new StringBuilder(downloadName);
		
		try {
			InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));
		
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.cacheControl(CacheControl.noCache())
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
					.body(resource);
		} catch(Exception e) {
			return new ResponseEntity<Long>(cd, HttpStatus.OK);
		}
	}

	// 게시글 수정
	@PostMapping(value = "/mod/{cd}")
	public String sweetBoardMod(@Valid SweetBoardDto sweetBoardDto, BindingResult bindingResult,
			@RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, @RequestParam("boardAttachFile") List<MultipartFile> boardAttachFileList, Model model) {
		if (bindingResult.hasErrors()) {
			return "sweetboard/sweetForm";
		}
		try {
			System.out.println("추가될 첨부파일 리스트: "+boardAttachFileList);
			System.out.println("삭제될 첨부파일 리스트: "+sweetBoardDto.getBoardAttachCds());
			sweetBoardService.updateSweetBoard(sweetBoardDto, boardImgFileList, boardAttachFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글을 수정하는 과정에서 에러가 발생하였습니다.");
			return "sweetboard/sweetForm";
		}
		return "redirect:/sweetboard/list";
	}

	// 게시글 삭제
	@GetMapping(value = "/del/{cd}")
	public String sweetBoardDel(@PathVariable("cd") Long cd) {
		sweetBoardService.deleteSweetBoard(cd);
		return "redirect:/sweetboard/list";
	}

	// 좋아요 수 증가
	@GetMapping(value = "/dtl/like")
	@ResponseBody
	public HashMap<String, Integer> like(Model model, Long cd) {
		HashMap<String, Integer> map = new HashMap<>();
		sweetBoardService.addLike(cd);
		SweetBoardDto sweetBoardDto = sweetBoardService.getSweetBoardDtl(cd);
		map.put("count", sweetBoardDto.getLike_cnt());
		return map;
	}
	
	// 댓글 조회
	@GetMapping("/dtl/com/list")
	@ResponseBody
	public HashMap<String, List<SweetCommentDto>> listComment(@RequestParam("cd") Long cd, Model model) {
		HashMap<String, List<SweetCommentDto>> map = new HashMap<>(); 
		
		List<SweetCommentDto> sweetCommentDtoList = sweetCommentService.getSweetComment(cd);
		map.put("sweetCommentDtoList", sweetCommentDtoList);
		return map;	
	}
	
	// 댓글 저장
	@ResponseBody
	@PostMapping(value="/dtl/com/write")
	public HashMap<String, String> saveComment(@RequestParam("cd") Long cd, @RequestParam("content") String content, Principal principal) {
		String userId = principal.getName();
		sweetCommentService.saveComment(userId, Long.valueOf(cd), content); // cd 자료형 좀
		HashMap<String, String> map = new HashMap<>();
		map.put("result", "result");
		return map;
	}
	
	// 댓글 삭제
	@ResponseBody
	@PostMapping(value="/dtl/com/del")
	public HashMap<String, String> deleteComment(@RequestParam("commentCd") Long commentCd) {
		sweetCommentService.deleteComment(commentCd);
		HashMap<String, String> map = new HashMap<>();
		map.put("result", "result");
		return map;
	}
	
	// 스윗 닉네임 존재 여부
	@GetMapping("/existNickName")
	@ResponseBody
	public Map<String, String> existSweetNickName(Authentication auth) {
		Map<String, String> result = new HashMap<>();
		String nickName = sweetBoardService.existSweetNickName(auth.getName());
		result.put("nickName", nickName);
		
		return result;
	}
	
	// 한글파일 깨짐방지 파일명 인코딩
	private String encodeFileName(String fileName, String userAgent) throws IOException {
		String downloadName = null;
		if (userAgent.contains("Trident")) {
			downloadName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (userAgent.contains("Edge")) {
			downloadName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			downloadName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		return downloadName;
	}
}
