package com.weepl.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weepl.constant.Role;
import com.weepl.dto.MemberFormDto;
import com.weepl.entity.Member;
import com.weepl.repository.MemberRepository;
import com.weepl.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	//임의로 관리자 생성
	   @PostConstruct
	   private void createAdmin() {
	      //관리자
	      boolean check = memberService.checkIdDuplicate("admin");
	      if (check) // 이미 admin 계정이 있는 경우 관리자계정 생성하지않음
	         return;
	      MemberFormDto memberFormDto = new MemberFormDto();
	      memberFormDto.setName("관리자");
	      memberFormDto.setId("admin");
	      memberFormDto.setPwd("12341234");
	      memberFormDto.setEmail("admin@abc.com");
	      memberFormDto.setGender("W");
	      memberFormDto.setTel1("010");
	      memberFormDto.setTel2("1234");
	      memberFormDto.setTel3("5678");
	      memberFormDto.setBirY("1995");
	      memberFormDto.setBirM("10");
	      memberFormDto.setBirD("26");
	      memberFormDto.setAddr("대전광역시 서구 둔산서로 17");
	      memberFormDto.setAddrDtl("양호빌딩 6층");
	      memberFormDto.setAddrPost("35235");
	      Member member = Member.createMember(memberFormDto , passwordEncoder);
	      String password = passwordEncoder.encode(memberFormDto.getPwd());
	      member.setPwd(password);
	      member.setRole(Role.ADMIN);
	      memberService.saveMember(member);
	   }

	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}

	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
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
	public String loginMember(MemberRepository memberRepository) {
		return "/member/memberLoginForm";
	}
	
	// 로그인 에러시 폼
	@GetMapping(value = "/login/error")
	public String loginError(Model model, String cause) {
		System.out.println("에러이유:"+cause);
		if("failed".equals(cause)) {
			model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		} else if("locked".equals(cause)) {
			model.addAttribute("loginErrorMsg", "탈퇴처리한 회원입니다.");
		} else if("restricted".equals(cause)) {
			model.addAttribute("loginErrorMsg", "이용이 제한된 회원입니다.");
		}
		
		return "/member/memberLoginForm";
	}
	
	
}