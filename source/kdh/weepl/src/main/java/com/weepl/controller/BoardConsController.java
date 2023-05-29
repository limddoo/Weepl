package com.weepl.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping(value="/consList")
	public String BoardConsList(Model model){
		
		List<BoardConsListDto> boardConsListDto = boardConsService.boardList();
		model.addAttribute("boardCons",boardConsListDto);
		return "boardCons/boardConsList";
	}
	@GetMapping(value="/connConsForm")
	public String connConsForm(Authentication auth, Model model){
		
		
		if(auth != null) {
			User user =  (User) auth.getPrincipal();
			Member member = boardConsService.findMember(user.getUsername());
			BoardConsFormDto boardConsFormDto = BoardConsFormDto.of(member);
			model.addAttribute("boardConsFormDto",boardConsFormDto);
			
		}else {
		
			model.addAttribute("boardConsFormDto", new BoardConsFormDto());
		
		}
	return "boardCons/boardConsForm";
	}
	@GetMapping(value="/consForm")
	public String BoardConsForm(Model model) {
		
		return "boardCons/boardConsForm";
	}
	@PostMapping(value="/consForm")
	public String consApp(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model) throws Exception {
		
		if(bindingResult.hasErrors()){
			return "boardCons/boardConsForm";
		}
		if(boardConsFormDto.getMemberCd() == null) {
//			try {
				System.out.println(boardConsFormDto.getEmail());
				boardConsService.saveNmCons(boardConsFormDto);
//			} catch (Exception e) {
//				model.addAttribute("errorMessage", "상담신청 중 에러가 발생하였습니다.");
//				return "boardCons/boardConsForm";
//			}
			
		}else {
			try {
		
			boardConsService.saveCons(boardConsFormDto);
		}catch(Exception e) {
			model.addAttribute("errorMessage", "상담신청 중 에러가 발생하였습니다.");
			return "boardCons/boardConsForm";
		}
	}
		return "redirect:/boardCons/consList";
	}
	
	
	@GetMapping(value="/consDtl/{board_cons_cd}")
	public String boardConsDtl(@PathVariable("board_cons_cd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.BoardConsDtl(cd);
		model.addAttribute("boardCons",boardCons);
		return "boardCons/boardConsDtl";
	}
	
	@GetMapping(value="/modCons/{board_cons_cd}")
	public String modConsForm(@PathVariable("board_cons_cd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.ModConsForm(cd);
		model.addAttribute("boardConsFormDto",boardCons);
		return "boardCons/modConsForm";
	}
	
	@PostMapping(value="/modCons/{board_cons_cd}")
	public String consUpdate(@Valid BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "boardCons/modConsForm";
		}
		if(boardConsFormDto.getTitle() == null) {
			model.addAttribute("errorMessage", "제목은 필수 입력입니다.");
			return "boardCons/modConsForm";
		}else if(boardConsFormDto.getContent() == null) {
			model.addAttribute("errorMessage", "신청 내용은 필수 입력입니다.");
			return "boardCons/modConsForm";
		}else if(boardConsFormDto.getPwd() == null) {
			model.addAttribute("errorMessage", "비밀번호는 필수 입력입니다.");
			return "boardCons/modConsForm";
		}
		try {
			boardConsService.updateCons(boardConsFormDto);
		}catch(Exception e) {
			model.addAttribute("errorMessage", "상담신청 수정 중 에러가 발생했습니다");
		}
		
		return "redirect:/boardCons/consList";
	}
	
	@GetMapping(value="/consDel/{board_cons_cd}")
	public String consDelete(@PathVariable("board_cons_cd") Long cd) {
		boardConsService.deleteCons(cd);
		return "redirect:/boardCons/consList";
	}
		
	
}
