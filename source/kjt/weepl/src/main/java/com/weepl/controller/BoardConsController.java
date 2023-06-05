package com.weepl.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.BoardConsDto;
import com.weepl.dto.BoardConsFormDto;
import com.weepl.dto.BoardConsReplyDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsReply;
import com.weepl.entity.Member;
import com.weepl.service.BoardConsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boardCons")
@RequiredArgsConstructor
public class BoardConsController {

	private final BoardConsService boardConsService;

	// 게시판 상담 리스트
	@GetMapping(value = { "/consList", "/consList/{page}" })
	public String BoardConsList(@PathVariable("page") Optional<Integer> page, Model model) {
		// PageRequest.of 메소드를 통해 Pageable객체 생성. 해당 페이지 조회, 페이지 번호가 없으면 0페이지에서 3개 조회
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		// 조회조건과 페이징 정보를 파라미터로 넘겨 Page<Item> 객체를 반환받는다.
		Page<BoardConsDto> boardCons = boardConsService.getBoardConsPage(pageable);

		model.addAttribute("boardCons", boardCons); // 조회한 상품 데이터와 페이징 정보를 뷰에 전달한다
		model.addAttribute("maxPage", 5); // 상품 관리 하단에 보여줄 페이지 번호의 최대 개수이다
//		System.out.println(boardCons.getContent());
		return "boardCons/boardConsList";
	}

	@PostMapping(value = "/consForm")
	public String consApp(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model)
			throws Exception {

		if (bindingResult.hasErrors()) {
			return "boardCons/boardConsForm";
		}
		if (boardConsFormDto.getMemberCd() == null) {
//			System.out.println(boardConsFormDto.getEmail());
			boardConsService.saveNmCons(boardConsFormDto);

		} else {
			try {

				boardConsService.saveCons(boardConsFormDto);
			} catch (Exception e) {
				model.addAttribute("errorMessage", "상담신청 중 에러가 발생하였습니다.");
				return "boardCons/boardConsForm";
			}
		}
		return "redirect:/boardCons/consList";
	}
	
	// 게시판 상담 글 작성폼
	@GetMapping(value = "/connConsForm")
	public String connConsForm(Authentication auth, Model model) {
		// 로그인 한 유저의 경우, 일부항목이 이미 세팅되어있음
		if (auth != null) {
			User user = (User) auth.getPrincipal();
			Member member = boardConsService.findMember(user.getUsername());
			BoardConsFormDto boardConsFormDto = BoardConsFormDto.of(member);
			model.addAttribute("boardConsFormDto", boardConsFormDto);
		}
		// 비회원의 경우
		else {
			model.addAttribute("boardConsFormDto", new BoardConsFormDto());
		}
		return "boardCons/boardConsForm";
	}

	
//	@GetMapping(value = "/connModConsForm/{boarConsCd}")
//	public String connModConsForm(@PathVariable("boardConsCd") Long cd, Authentication auth, Model model) {
//		// 로그인 한 유저의 경우, 일부항목이 이미 세팅되어있음
//		BoardCons boardCons = boardConsService.ModConsForm(cd);
//		model.addAttribute("boardConsFormDto", boardCons);
//		if (auth != null) {
//			User user = (User) auth.getPrincipal();
//			Member member = boardConsService.findMember(user.getUsername());
//			BoardConsFormDto boardConsFormDto = BoardConsFormDto.of(member);
//			model.addAttribute("boardConsFormDto", boardConsFormDto);
//		}
//		// 비회원의 경우
//		else {
//			model.addAttribute("boardConsFormDto", new BoardConsFormDto());
//		}
//		return "boardCons/modConsForm";
//	}
	
	@GetMapping(value = "/connModConsForm/{boardConsCd}")
	public String connModConsForm(@PathVariable("boardConsCd") Long boardConsCd, Authentication auth, Model model) {
	    // 로그인 한 유저의 경우, 일부 항목이 이미 세팅되어 있음
	    BoardCons boardCons = boardConsService.ModConsForm(boardConsCd);

	    // 비밀번호 정보 추출
	    String password = boardCons.getPwd();

	    // boardConsFormDto 객체 생성 및 비밀번호 설정
	    BoardConsFormDto boardConsFormDto = new BoardConsFormDto();
	    boardConsFormDto.setPwd(password);

	    model.addAttribute("boardCons", boardCons);
	    model.addAttribute("boardConsFormDto", boardConsFormDto);

	    return "boardCons/modConsForm";
	}
	
	@GetMapping(value = "/modCons/{boardConsCd}")
	public String modConsForm(@PathVariable("boardConsCd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.ModConsForm(cd);
		model.addAttribute("boardConsFormDto", boardCons);
		return "boardCons/modConsForm";
	}

	@PostMapping(value = "/modCons/{boardConsCd}")
	public String consUpdate(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
		    return "boardCons/modConsForm";
		}

	    try {
	        if (boardConsFormDto.getMemberCd() == null) {
	            boardConsService.updateCons(boardConsFormDto);
	        } else {
	            boardConsService.saveCons(boardConsFormDto);
	        }
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "상담신청 수정 중 에러가 발생했습니다");
	        return "boardCons/modConsForm";
	    }

	    return "redirect:/boardCons/consList";
	}

	@GetMapping(value = "/consDtl/{boardConsCd}")
	public String boardConsDtl(@PathVariable("boardConsCd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.boardConsDtl(cd);
		BoardConsReply boardConsReply = boardConsService.getBoardConsReply(boardCons);
		if(boardConsReply == null) {
			boardConsReply = new BoardConsReply();
			boardConsReply.setBoardCons(boardCons);
		}
		model.addAttribute("boardConsReply", boardConsReply);
		model.addAttribute("boardCons", boardCons);
		System.out.println(model);
		return "boardCons/boardConsDtl";
	}

	
	



	@GetMapping(value = "/consDel/{boardConsCd}")
	public String consDelete(@PathVariable("boardConsCd") Long cd) {
		boardConsService.deleteCons(cd);
		return "redirect:/boardCons/consList";
	}

	// Ajax: 로그인상태에서 게시판상담 상세글 접근시 접근한 유저가 해당글의 작성자인지 알기위한 메서드
	@PostMapping(value="/getBoardMember")
	@ResponseBody
	public Map<String,String> getBoardMember(@RequestParam("boardCd") Long boardCd) {
		Map<String, String> result = new HashMap<>();
		Member member = boardConsService.findBoardMember(boardCd);
		if(member != null) {
			result.put("result", member.getId());
		}
		
		return result;
	}
	
	// Ajax: 입력한 게시글 비밀번호가 일치하면 "ENTER" 값리턴
	@PostMapping(value="/confirmBoardPwd")
	@ResponseBody
	public Map<String, String> confirmBoardPwd(@RequestParam("boardCd") Long boardCd, @RequestParam("boardPwd") String boardPwd) {
		Map<String, String> result = new HashMap<>();
		result.put("result", boardConsService.confirmPwd(boardCd, boardPwd));
		System.out.println(boardConsService.confirmPwd(boardCd, boardPwd));
		return result;
	}
	
	@PostMapping(value="/replyBoardCons")
	public String replyBoardCons(@Valid BoardConsReplyDto boardConsReplyDto, String boardConsCd, BindingResult bindingResult) {
		System.out.println("답변내용:"+boardConsReplyDto);
		BoardCons savedBoardCons = boardConsService.replyBoardCons(boardConsReplyDto);
		return "redirect:/boardCons/consDtl/"+savedBoardCons.getCd();
	}
}
