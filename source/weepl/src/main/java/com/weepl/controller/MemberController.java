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
		boolean check1 = memberService.checkIdDuplicate("admin");
		if (check1) // 이미 admin 계정이 있는 경우 관리자계정 생성하지않음
			return;
		MemberFormDto memberFormDto = MemberFormDto.createAdmin();
		Member member = Member.createMember(memberFormDto, passwordEncoder);
		member.setNickName("관리자");
		member.setRole(Role.ADMIN);
		memberService.saveMember(member);

		// 유저1(내담자)
		boolean check2 = memberService.checkIdDuplicate("hong");
		if (check2)
			return;
		MemberFormDto memberFormDto2 = MemberFormDto.createUser1();
		Member member2 = Member.createMember(memberFormDto2, passwordEncoder);
		member2.setRole(Role.CLIENT);
		memberService.saveMember(member2);
		
		// 유저2(전문상담사)
		boolean check3 = memberService.checkIdDuplicate("kim");
		if (check3)
			return;
		MemberFormDto memberFormDto3 = MemberFormDto.createUser2();
		Member member3 = Member.createMember(memberFormDto3, passwordEncoder);
		member3.setRole(Role.COUNSELOR);
		member3.setNickName("스윗테스트");
		memberService.saveMember(member3);
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
		System.out.println("에러이유:" + cause);
		if ("failed".equals(cause)) {
			model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		} else if ("locked".equals(cause)) {
			model.addAttribute("loginErrorMsg", "탈퇴처리한 회원입니다.");
		} else if ("restricted".equals(cause)) {
			model.addAttribute("loginErrorMsg", "이용이 제한된 회원입니다.");
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
	@PostMapping(value = "/findIdByNameAndTel")
	@ResponseBody
	public HashMap<String, String> findIdByNameAndTel(@RequestParam("name") String name, @RequestParam("tel1") String tel1, @RequestParam("tel2") String tel2, @RequestParam("tel3") String tel3) {
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
	@PostMapping(value = "/findPwdByEmail")
	@ResponseBody
	public HashMap<String, String> findPwdByEmail(@RequestParam("id") String id, @RequestParam("name") String name, @RequestParam("email") String email) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		String memberId = memberService.findPwd(id, name, email);
		if (memberId != null) {
			String pwd = registerMail.sendSimpleMessageforPwd(email);
			memberService.updateMemberPwd(id, pwd, passwordEncoder);
			map.put("result", "true");
		} else {
			map.put("result", "false");
		}
		return map;
	}

	// id 중복체크(Ajax)
	@PostMapping(value = "/exists")
	@ResponseBody
	public HashMap<String, Object> checkMidDuplicate(@RequestParam("id") String id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", memberService.checkIdDuplicate(id));
		return map;
	}

	// 이메일 인증(Ajax)
	@PostMapping(value = "/mailConfirm")
	@ResponseBody
	public HashMap<String, Object> mailConfirm(@RequestParam("email") String email) throws Exception {
		HashMap<String, Object> map = new HashMap<>();
		map.put("result", registerMail.sendSimpleMessage(email));
		return map;
	}
}
