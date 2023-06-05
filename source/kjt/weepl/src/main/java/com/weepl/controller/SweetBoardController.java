package com.weepl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.SweetBoardDto;
import com.weepl.dto.SweetCommentDto;
import com.weepl.dto.SweetSearchDto;
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
	public String sweetBoardManage(SweetSearchDto sweetSearchDto, @PathVariable("page") Optional<Integer> page,
			Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 15);

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
	public String addSweetBoard(@Valid SweetBoardDto sweetBoardDto, BindingResult bindingResult, Model model,
			@RequestParam("boardImgFile") List<MultipartFile> boardImgFileList,
			@RequestParam("boardAttachFile") List<MultipartFile> boardAttachFileList) {
		
		if (bindingResult.hasErrors()) {
			return "sweetboard/sweetForm";
		}
		if (boardImgFileList.get(0).isEmpty() && sweetBoardDto.getCd() == null) {
			model.addAttribute("errorMessage", "이미지를 하나 이상 등록해주세요.");
			return "sweetboard/sweetForm";
		}
		try {
			sweetBoardService.saveSweetBoard(sweetBoardDto, boardImgFileList, boardAttachFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글을 저장하는 과정에서 에러가 발생하였습니다.");
			return "sweetboard/sweetForm";
		}
		return "redirect:/sweetboard/list";
	}

	// 게시글 상세보기 화면
	@GetMapping(value="/dtl1/{cd}") 
	public String sweetBoardDtl(Model model, @PathVariable("cd") Long cd) { 
		try { 
			SweetBoardDto sweetBoardDto = sweetBoardService.getSweetBoardDtl(cd);
			SweetCommentDto sweetCommentDto = sweetCommentService.getSweetComment(cd);
			
			model.addAttribute("sweetBoardDto", sweetBoardDto);
			model.addAttribute("sweetCommentDto", sweetCommentDto);
		} 
			catch (EntityNotFoundException e) {
				model.addAttribute("errorMessage", "존재하지 않는 글입니다.");
				model.addAttribute("sweetBoardDto", new SweetBoardDto()); 
				return "sweetboard/sweetForm"; // 추후 문제되면 sweetboard/sweetDetail 경로로 수정
		}
		return "sweetboard/sweetFrom";
	}
	
	// 게시글 상세보기 화면
	@GetMapping(value = "/dtl/{cd}")
	public String swettBoardDtl(Model model, @PathVariable("cd") Long cd) {
		SweetBoardDto sweetBoardDto = sweetBoardService.getSweetBoardDtl(cd); 
		List<SweetCommentDto> comments = sweetBoardDto.getSweetCommentDtoList();
		// 댓글
		if(comments != null && !comments.isEmpty()) {
			model.addAttribute("comments", comments);
		}
		model.addAttribute("sweetBoardDto", sweetBoardDto);
		return "sweetboard/sweetDetail";
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

	// 게시글 수정
	@PostMapping(value = "/mod/{cd}")
	public String sweetBoardMod(@Valid SweetBoardDto sweetBoardDto, BindingResult bindingResult,
			@RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, List<MultipartFile> boardAttachFileList, Model model) {
		if (bindingResult.hasErrors()) {
			return "sweetboard/sweetForm";
		}
		if (boardImgFileList.get(0).isEmpty() && sweetBoardDto.getCd() == null) {
			model.addAttribute("errorMessage", "이미지를 하나 이상 등록해주세요");
			return "sweetboard/sweetForm";
		}
		try {
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
	
//	// 좋아요 수 업데이트 - 1 (fail
//	@PatchMapping(value="dtl/{cd}")
//	public @ResponseBody ResponseEntity<Long> updateLikeCnt(Long cd) {
//		sweetBoardService.addLike(cd);
//		return new ResponseEntity<Long>(cd, HttpStatus.OK);
//	}
//	// 좋아요 수 업데이트 - 2 (fail
//	@GetMapping(value="dtl/likeUpdate")
//	@ResponseBody
//	public HashMap<String, String> likeUpdate(Long cd, int count) {
//		HashMap<String, String> map = new HashMap<>();
//		map.put(cd, count);
//		return map;
//	}
	// 좋아요 수 증가 - 3
	@PatchMapping(value="/dtl/addLike")
	public @ResponseBody ResponseEntity<Long> addLike(Long cd, @Valid SweetBoardDto sweetBoardDto, int count) {
		Long savedSweetBoard = sweetBoardService.addLike(cd, sweetBoardDto, count);
		return new ResponseEntity<Long>(savedSweetBoard, HttpStatus.OK);
	}
	
	// 댓글 조회 
	@SuppressWarnings("rawtypes")
	@PostMapping("/dtl/{cd}/com")
	public ResponseEntity saveComment(@PathVariable Long cd, @RequestBody SweetCommentDto sweetCommentDto) {
		return ResponseEntity.ok(sweetCommentService.saveComment(cd, sweetCommentDto));
	}
	
	// 댓글 작성
	@PostMapping(value="/dtl/com/write")
	public String writeComment(@RequestParam("cd") Long cd, @RequestParam("comment") String comment) throws Exception {
		SweetCommentDto sweetCommentDto = sweetCommentService.getSweetComment(cd);
		sweetCommentDto.setComment(comment);
		sweetCommentDto.setCd(cd);
//		sweetCommentService.addComment(sweetCommentDto); // 코드가 엇박자임
		return "redirect:/sweetboard/dtl";
	}
	
	// 댓글 삭제
	@DeleteMapping(value="dtl/{cd}/com/{comCd}")
	public @ResponseBody ResponseEntity<Long> deleteSweetComment(@PathVariable("cd") Long cd, Principal principal) {
		sweetCommentService.deleteComment(cd);
		return new ResponseEntity<Long>(cd, HttpStatus.OK);
	}
}
