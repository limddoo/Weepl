package com.weepl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

import com.weepl.dto.BoardConsFormDto;
import com.weepl.dto.MyConsDto;
import com.weepl.dto.MypageFormDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsReply;
import com.weepl.entity.Member;
import com.weepl.service.BoardConsService;
import com.weepl.service.MypageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
	private final MypageService mypageService;
	
	private final BoardConsService boardConsService;
	
	private final PasswordEncoder passwordEncoder;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MypageController.class);

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
		if (!passwordEncoder.matches(ppwd, member.getPwd())) {
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
		if (!passwordEncoder.matches(pwd, member.getPwd())) {
			model.addAttribute("errorMessage", "비밀번호가 일치하지않습니다.");
		} else {
			mypageService.quitMember(user.getUsername());
			model.addAttribute("result", "회원탈퇴가 완료되었습니다.");
			SecurityContextHolder.clearContext();
		}
		return "redirect:/";
	}

	// 사용자의 내 상담일정 보기
	@GetMapping("/myReservation")
	public String myReservation() {
		LOGGER.info("myReservation 호출");
		return "/mypage/myReservation";
	}

	@ResponseBody
	@GetMapping("/viewMyReservation") // ajax 데이터 전송 URL //
	public List<Map<String, Object>> viewMyReservation(Principal principal) {
		LOGGER.info("viewMyReservation() 호출");
		String name = principal.getName();
		return mypageService.viewMyReservation(name);
	}
	
	@GetMapping("/untactConsForm")
	public String untactConsForm() {
		return "/mypage/connUntactCons";
	}

	@GetMapping("/chattingForm")
	public String chattingForm() {
		return "/mypage/chatting";
	}

@GetMapping(value = { "/myConsList", "/myConsList/{page}" })
	public String myConsList(@PathVariable("page") Optional<Integer> page, Model model, Authentication auth) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

		// 조회조건과 페이징 정보를 파라미터로 넘겨 Page<Item> 객체를 반환받는다.
		Page<MyConsDto> myConsList = mypageService.getMyConsPage(pageable, auth.getName());

		model.addAttribute("myConsList", myConsList); // 조회한 상품 데이터와 페이징 정보를 뷰에 전달한다
		model.addAttribute("maxPage", 5); // 상품 관리 하단에 보여줄 페이지 번호의 최대 개수이다
		System.out.println(myConsList.getContent());
		return "mypage/myConsList";
	}
	
	@GetMapping(value = "/myConsDtl/{boardConsCd}")
	public String boardConsDtl(@PathVariable("boardConsCd") Long cd, Model model) {
		BoardCons boardCons = boardConsService.ModConsForm(cd);
		System.out.println(boardCons);
		BoardConsReply boardConsReply = boardConsService.getBoardConsReply(boardCons);
		if (boardConsReply == null) {
			boardConsReply = new BoardConsReply();
			boardConsReply.setBoardCons(boardCons);
		}
		model.addAttribute("boardConsReply", boardConsReply);
		model.addAttribute("boardCons", boardCons);
		System.out.println(model);
		return "mypage/myConsDtl";
	}
	
	@GetMapping(value = "/modMyCons/{boardConsCd}")
	public String modConsForm(@PathVariable("boardConsCd") Long cd, Model model) {
	    BoardCons boardCons = boardConsService.ModConsForm(cd);
	    model.addAttribute("boardConsFormDto", boardCons);
	    return "mypage/modMyCons";
	}
	
	@PostMapping(value = "/modMyCons")
	public String myConsUpdate(BoardConsFormDto boardConsFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "mypage/modMyCons";
		}
		if(boardConsFormDto.getTitle()== null) {
			model.addAttribute("errorMessage", "제목은 필수 입력입니다.");
			return "mypage/modMyCons";
		} else if (boardConsFormDto.getContent() == null) {
			model.addAttribute("errorMessage", "신청 내용은 필수 입력입니다.");
			return "mypage/modMyCons";
		} 
		try {
			boardConsService.updateCons(boardConsFormDto);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "상담신청 수정 중 에러가 발생했습니다");
		}
		return "redirect:/mypage/myConsList";
	}
	
	
	// 게시판 상담 글 삭제(삭제여부:Y로 설정)
		@GetMapping(value = "/myConsDel/{board_cons_cd}")
		public String myConsDelete(@PathVariable("board_cons_cd") Long cd) {
			boardConsService.deleteCons(cd);
			return "redirect:/mypage/myConsList";
		}
}
