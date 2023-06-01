package com.weepl.controller;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weepl.dto.MypageFormDto;
import com.weepl.entity.Member;
import com.weepl.service.MypageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
	private final MypageService mypageService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/connMypageForm")
	public String connMypageForm() {
		return "mypage/connMypage";
	}

	@PostMapping("/connMypage")
	public String connMypage(Authentication auth, @RequestParam("pwd") String pwd, Model model) {
		User user = (User) auth.getPrincipal();
		Member member = mypageService.findMember(user.getUsername());
		if (member != null && passwordEncoder.matches(pwd, member.getPwd())) {
			System.out.println("비밀번호 일치");
			MypageFormDto mypageDto = MypageFormDto.of(member);
			model.addAttribute("mypageFormDto", mypageDto);
			return "mypage/modMyInfo";
		} 

		model.addAttribute("errorMsg", "비밀번호가 일치하지않습니다.");
		return "mypage/connMypage";
	}

	@GetMapping("/connMypage")
	public String modForm(Authentication auth, Model model) {
		User user = (User) auth.getPrincipal();
		Member member = mypageService.findMember(user.getUsername());
		model.addAttribute("mypageFormDto", MypageFormDto.of(member));
		return "mypage/modMyInfo";
	}

	@PostMapping("/modMyInfo")
	public String modMyInfo(@Valid MypageFormDto mypageFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "mypage/modMyInfo";
		}
		
		try {
			mypageService.updateMember(mypageFormDto);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "mypage/modMyInfo";
		}
		
		model.addAttribute("result", "수정이 완료되었습니다!");
		return "mypage/modMyInfo";
	}
	
	@GetMapping("/modMyPwd")
	public String modPwdForm(Model model) {
		return "mypage/modPwd";
	}
	
	@PostMapping("/modMyPwd")
	public String modPwd(Authentication auth, String pwd, String ppwd, Model model) {
		User user = (User) auth.getPrincipal();
		Member member = mypageService.findMember(user.getUsername());
		if(!passwordEncoder.matches(ppwd, member.getPwd())) {
			model.addAttribute("errorMessage", "현재 비밀번호가 일치하지않습니다.");
		} else {
			mypageService.updateMemberPwd(member.getId(), pwd, passwordEncoder);
			model.addAttribute("result", "수정이 완료되었습니다!");
		}
		return "mypage/modPwd";
	}
	
	@GetMapping("/quitMember")
	public String quitMemberForm(Model model) {
		return "mypage/quitMember";
	}
	
	@PostMapping("/quitMember")
	public String quitMember(Authentication auth, String pwd, Model model) {
		User user = (User) auth.getPrincipal();
		Member member = mypageService.findMember(user.getUsername());
		if(!passwordEncoder.matches(pwd, member.getPwd())) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지않습니다.");
		} else {
			mypageService.quitMember(user.getUsername());
			model.addAttribute("result", "회원탈퇴가 완료되었습니다.");
			SecurityContextHolder.clearContext();
		}
		return "redirect:/";
	}
}
