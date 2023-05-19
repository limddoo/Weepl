package com.weepl.controller;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

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

	// 임의로 관리자 생성
	@PostConstruct
	private void createAdmin() {
		// 관리자
		boolean check = memberService.checkIdDuplicate("admin");
		if (check) // 이미 admin 계정이 있는 경우 관리자계정 생성하지않음
			return;
		MemberFormDto memberFormDto = MemberFormDto.createAdmin();
		Member member = Member.createMember(memberFormDto, passwordEncoder);
		String password = passwordEncoder.encode(memberFormDto.getPwd());
		member.setPwd(password);
		member.setRole(Role.ADMIN);
		memberService.saveMember(member);
	}
	
	// 회원가입 폼 띄우기 전 약관동의 페이지
	@GetMapping(value = "/beforeRegister")
	public String agreeForm(Model model) {
		return "/member/registerAgree";
	}

	// 회원가입 폼
	@GetMapping(value = "/new")
	public String memberForm(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto("CLIENT"));
		return "/member/memberForm";
	}
	
	// 신규회원 등록
	@PostMapping(value = "/new")
	public String newMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/member/memberForm";
		}

		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "/member/memberForm";
		}
		return "/member/memberLoginForm";
	}

	// 로그인 폼
	@GetMapping(value = "/login")
	public String loginMember() {
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
		}
		
		return "/member/memberLoginForm";
	}

	// 아이디 찾기 폼
	@GetMapping(value = "/findId")
	public String findId(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "/member/findId";
	}
	
	// 이름과 전화번호로 아이디 찾기(Ajax)
	@GetMapping(value = "/findIdByNameAndTel")
	@ResponseBody
	public HashMap<String, String> findIdByNameAndTel(String name, String tel1, String tel2, String tel3) {
		HashMap<String, String> map = new HashMap<>();
		map.put("result", memberService.findId(name, tel1, tel2, tel3));
		return map;
	}
	
	// 비밀번호 찾기 폼
	@GetMapping(value = "/findPwd")
	public String findPwd(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "/member/findPwd";
	}
	
	// 비밀번호 찾기(Ajax)
	@GetMapping(value = "/findPwdByEmail")
	@ResponseBody
	public HashMap<String, String> findPwdByEmail(String id, String name, String email) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		String memberId = memberService.findPwd(id, name, email);
		if(memberId != null) {
			String pwd = registerMail.sendSimpleMessageforPwd(email);
			memberService.updateMemberPwd(id, pwd, passwordEncoder);
			map.put("result", "true");
		} else {
			map.put("result", "false");
		}
		return map;
	}

	// id 중복체크(Ajax)
	@GetMapping(value = "/exists")
	@ResponseBody
	public HashMap<String, Object> checkMidDuplicate(String id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", memberService.checkIdDuplicate(id));
		return map;
	}

	// 이메일 인증(Ajax)
	@GetMapping(value = "/mailConfirm")
	@ResponseBody
	public HashMap<String, Object> mailConfirm(String email) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", registerMail.sendSimpleMessage(email));
		return map;
	}
}
