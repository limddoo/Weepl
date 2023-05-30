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

import com.weepl.dto.BoardConsFormDto;
import com.weepl.dto.BoardConsListDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.Member;
import com.weepl.service.BoardConsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boardCons")
@RequiredArgsConstructor
public class BoardConsController {

	private final BoardConsService boardConsService;

	@GetMapping(value = { "/consList", "/consList/{page}" })
	public String BoardConsList(@PathVariable("page") Optional<Integer> page, Model model) {
		// PageRequest.of 메소드를 통해 Pageable객체 생성. 해당 페이지 조회, 페이지 번호가 없으면 0페이지에서 3개 조회
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		// 조회조건과 페이징 정보를 파라미터로 넘겨 Page<Item> 객체를 반환받는다.
		Page<BoardConsListDto> boardCons = boardConsService.getBoardConsPage(pageable);

		model.addAttribute("boardCons", boardCons); // 조회한 상품 데이터와 페이징 정보를 뷰에 전달한다
		model.addAttribute("maxPage", 5); // 상품 관리 하단에 보여줄 페이지 번호의 최대 개수이다
		return "boardCons/boardConsList";
	}

	@GetMapping(value = "/connConsForm")
	public String connConsForm(Authentication auth, Model model) {

		if (auth != null) {
			User user = (User) auth.getPrincipal();
			Member member = boardConsService.findMember(user.getUsername());
			BoardConsFormDto boardConsFormDto = BoardConsFormDto.of(member);
			model.addAttribute("boardConsFormDto", boardConsFormDto);

		} else {

			model.addAttribute("boardConsFormDto", new BoardConsFormDto());

		}
		return "boardCons/boardConsForm";
	}

	@GetMapping(value = "/consForm")
	public String BoardConsForm(Model model) {

		return "boardCons/boardConsForm";
	}

	@PostMapping(value = "/consForm")
	public String consApp(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model)
			throws Exception {

		if (bindingResult.hasErrors()) {
			return "boardCons/boardConsForm";
		}
		if (boardConsFormDto.getMemberCd() == null) {
//			try {
			System.out.println(boardConsFormDto.getEmail());
			boardConsService.saveNmCons(boardConsFormDto);
//			} catch (Exception e) {
//				model.addAttribute("errorMessage", "상담신청 중 에러가 발생하였습니다.");
//				return "boardCons/boardConsForm";
//			}

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

	@GetMapping(value = "/consDtl/{board_cons_cd}")
	public String boardConsDtl(@PathVariable("board_cons_cd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.boardConsDtl(cd);
		model.addAttribute("boardCons", boardCons);
		return "boardCons/boardConsDtl";
	}

	@GetMapping(value = "/modCons/{board_cons_cd}")
	public String modConsForm(@PathVariable("board_cons_cd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.ModConsForm(cd);
		model.addAttribute("boardConsFormDto", boardCons);
		return "boardCons/modConsForm";
	}

	@PostMapping(value = "/modCons/{board_cons_cd}")
	public String consUpdate(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "boardCons/modConsForm";
		}
		if (boardConsFormDto.getTitle() == null) {
			model.addAttribute("errorMessage", "제목은 필수 입력입니다.");
			return "boardCons/modConsForm";
		} else if (boardConsFormDto.getContent() == null) {
			model.addAttribute("errorMessage", "신청 내용은 필수 입력입니다.");
			return "boardCons/modConsForm";
		} else if (boardConsFormDto.getPwd() == null) {
			model.addAttribute("errorMessage", "비밀번호는 필수 입력입니다.");
			return "boardCons/modConsForm";
		}
		try {
			boardConsService.updateCons(boardConsFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상담신청 수정 중 에러가 발생했습니다");
		}

		return "redirect:/boardCons/consList";
	}

	@GetMapping(value = "/consDel/{board_cons_cd}")
	public String consDelete(@PathVariable("board_cons_cd") Long cd) {
		boardConsService.deleteCons(cd);
		return "redirect:/boardCons/consList";
	}

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
	
	@PostMapping(value="/confirmBoardPwd")
	@ResponseBody
	public Map<String, String> confirmBoardPwd(@RequestParam("boardCd") Long boardCd, @RequestParam("boardPwd") String boardPwd) {
		Map<String, String> result = new HashMap<>();
		result.put("result", boardConsService.confirmPwd(boardCd, boardPwd));
		System.out.println(boardConsService.confirmPwd(boardCd, boardPwd));
		return result;
	}
}
