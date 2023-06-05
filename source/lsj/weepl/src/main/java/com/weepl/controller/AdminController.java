package com.weepl.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.MemberSearchDto;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.entity.Member;
import com.weepl.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final AdminService adminService;
	private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	
	@PostMapping(value = "/modMemberInfo/{memCd}")
	public String modMemberInfo(@Valid ModMemberInfoDto modMemberInfoDto, BindingResult bindingResult, Model model)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "admin/modMemberInfoForm";
		}

		try {
			adminService.updateMember(modMemberInfoDto);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "admin/modMemberInfoForm";
		}

		model.addAttribute("result", "수정이 완료되었습니다!");
		return "redirect:/admin/memberList";
	}

	@GetMapping(value = "/modMemberInfo/{memCd}")
	public String modMemberInfoForm(@PathVariable("memCd") Long memCd, Model model) {
		Member member = adminService.findOne(memCd);
		if (member != null) {
			ModMemberInfoDto modMemberInfoDto = ModMemberInfoDto.of(member);
			model.addAttribute("modMemberInfoDto", modMemberInfoDto);
		} else {
			// 처리할 로직 추가 (멤버를 찾을 수 없는 경우)
			model.addAttribute("errorMessage", "멤버를 찾을 수 없습니다."); // 에러 메시지 설정
			return "redirect:/admin/memberList";
		}
		return "admin/modMemberInfoForm";
	}

	@GetMapping("/deleteMember/{memCd}")
	public String deleteMemberInfo(@PathVariable("memCd") Long memCd, Model model) throws Exception {
		adminService.deleteMember(memCd);
		return "redirect:/admin/memberList";
	}

	@GetMapping("/restrictMember/{memCd}")
	public String restrictMember(@PathVariable("memCd") Long memCd, Model model) throws Exception {
		adminService.restrictMemberForOneWeek(memCd);
		return "redirect:/admin/memberList";
	}

	@PostMapping("/restrictMember/{memCd}")
	public ResponseEntity<String> restrictMember(@PathVariable("memCd") Long memCd) {
	    adminService.restrictMemberForOneWeek(memCd);
	    return ResponseEntity.ok("회원이 제한되었습니다.");
	}

	@PostMapping("/{memCd}/cancel-restriction")
    public ResponseEntity<String> cancelMemberRestriction(@PathVariable("memCd") Long memCd) {
        adminService.cancelMemberRestriction(memCd);
        return ResponseEntity.ok("회원 제한이 해제되었습니다.");
    }
	
	 @GetMapping("/{memCd}/cancel-restriction")
	    public String cancelMemberRestriction(@PathVariable("memCd") Long memCd, Model model) throws Exception {
	        adminService.cancelMemberRestriction(memCd);
	        return "redirect:/admin/reMemberList";
	    }
	 
	 @GetMapping(value = { "/memberList", "/memberList/{page}" })
		public String memberList(MemberSearchDto memberSearchDto, @PathVariable("page") Optional<Integer> page,
				Model model) {
			int pageNumber = page.orElse(0);
			
			if(pageNumber < 0) {
				pageNumber = 0;
			}
			
			Pageable pageable = PageRequest.of(pageNumber, 5);
			Page<Member> memberList = adminService.getAdminMemberInfoPage(memberSearchDto, pageable);

			// 마지막 페이지인 경우에 대한 처리
			if (pageNumber >= memberList.getTotalPages() && memberList.getTotalPages() > 0) {
				// 현재 페이지를 마지막 페이지로 설정
				pageNumber = memberList.getTotalPages() - 1;
				// 다시 페이지 요청
				pageable = PageRequest.of(pageNumber, 5);
				memberList= adminService.getAdminMemberInfoPage(memberSearchDto, pageable);
			}
			model.addAttribute("memberList", memberList);
			model.addAttribute("memberSearchDto", memberSearchDto);
			model.addAttribute("maxPage", 5);
			return "admin/memberList";
		}
	 
	 @GetMapping(value = { "/reMemberList", "/reMemberList/{page}" })
	 public String reMemberList(MemberSearchDto memberSearchDto, @PathVariable("page") Optional<Integer> page,
	                            Model model) {
	     int pageNumber = page.orElse(0);

	     if (pageNumber < 0) {
	         pageNumber = 0;
	     }

	     Pageable pageable = PageRequest.of(pageNumber, 5);
	     Page<Member> reMemberList = adminService.getAdminMemberInfoPage(memberSearchDto, pageable);

	     // 마지막 페이지인 경우에 대한 처리
	     if (pageNumber >= reMemberList.getTotalPages() && reMemberList.getTotalPages() > 0) {
	         // 현재 페이지를 마지막 페이지로 설정
	         pageNumber = reMemberList.getTotalPages() - 1;
	         // 다시 페이지 요청
	         pageable = PageRequest.of(pageNumber, 5);
	         reMemberList = adminService.getAdminMemberInfoPage(memberSearchDto, pageable);
	     }
	     model.addAttribute("reMemberList", reMemberList);
	     model.addAttribute("memberSearchDto", memberSearchDto);
	     model.addAttribute("maxPage", reMemberList.getTotalPages()); // 수정: 실제 최대 페이지 수를 사용
	     return "admin/reMemberList";
	 }
	@GetMapping("/untactConsForm")
    public String untactConsForm(Model model) {
		model.addAttribute("reserveApplyList", adminService.getReserveApplyList());
    	return "admin/untactCons";
    }
	
	@GetMapping("/chattingForm/{reserveApplyCd}")
    public String chattingForm(Model model, @PathVariable(value="reserveApplyCd") Long reserveApplyCd) {
		model.addAttribute("reserveApplyCd", reserveApplyCd);
    	return "admin/chatting";
    }
	
	@GetMapping("/ucMngForm")
    public String ucMngForm() {
    	return "admin/untactConsSchMng";
    }
	
	@PostMapping(value="/ucSchCreate")
	@ResponseBody
	public HashMap<String, String> ucMSchCreate(@RequestParam(value="schDate")String schDate, @RequestParam(value="am")String schTimeAm, @RequestParam(value="pm")String schTimePm, Model model) {
		HashMap<String, String> map = new HashMap<>();
		String[] schDateArray = schDate.split(",");
		List<String> schDateList = Arrays.asList(schDateArray);
		adminService.saveReserveSchedule(schDateList, schTimeAm, schTimePm);
		map.put("result","등록 성공!");
		return map;
	}
	
	@GetMapping(value="/ucSchDelete")
	@ResponseBody
	public HashMap<String, String> ucSchDelete(Long selectedId) {
		HashMap<String, String> map = new HashMap<>();
		System.out.println("선택된 스케쥴아이디: "+selectedId);
		adminService.deleteReserveScedult(selectedId);
		map.put("result","삭제 성공!");
		return map;
	}
}
