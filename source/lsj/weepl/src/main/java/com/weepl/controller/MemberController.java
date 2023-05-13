package com.weepl.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.constant.Role;
import com.weepl.dto.MemberFormDto;
import com.weepl.entity.Member;
import com.weepl.service.MemberService;
import com.weepl.service.RegisterMail;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final RegisterMail registerMail;

	//임의로 관리자 생성
	   @PostConstruct
	   private void createAdmin() {
	      //관리자
	      boolean check = memberService.checkIdDuplicate("admin");
	      if (check) // 이미 admin 계정이 있는 경우 관리자계정 생성하지않음
	         return;
	      MemberFormDto memberFormDto = MemberFormDto.createAdmin();
	      Member member = Member.createMember(memberFormDto , passwordEncoder);
	      String password = passwordEncoder.encode(memberFormDto.getPwd());
	      member.setPwd(password);
	      member.setRole(Role.ADMIN);
	      memberService.saveMember(member);
	   }

	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto("CLIENT"));
		return "member/memberForm";
	}

	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		System.out.println(memberFormDto.getRole());
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		return "redirect:/";
	}
	
	@GetMapping(value="/login")
	public String loginMember() {
		return "/member/memberLoginForm";
	}
	
	@GetMapping(value="/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "/member/memberLoginForm";
	}
	
	// id 중복체크(ajax)
	@GetMapping(value="/exists")
	@ResponseBody
	public HashMap<String, Object> checkMidDuplicate(String id){
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", memberService.checkIdDuplicate(id));
		return map;
	}
	
	// 이메일 인증
	@GetMapping(value="/mailConfirm")
	@ResponseBody
	public HashMap<String, Object> mailConfirm(String email) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", registerMail.sendSimpleMessage(email));
		return map;
	}
}
