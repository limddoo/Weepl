package com.weepl.controller;



import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.MhTestResultDto;
import com.weepl.service.MhTestResultService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/mhTest")
@RequiredArgsConstructor
public class MhTestController {
	
	private final MhTestResultService mhTestResultService;
	
	@GetMapping(value="testMain")
	public String TestMain() {
		return "healthTest/healthTestMain";
	}
	
	
	
	@GetMapping(value="applyTest01")
	public String Test01(Model model) {
		
		model.addAttribute("major_div","맞춤형 1:1검사");
		
		return "healthTest/applyTest01";
	}
	@GetMapping(value="applyTest02")
	public String Test02(Model model) {
		
		model.addAttribute("major_div","상황별검사");
		
		return "healthTest/applyTest02";
	}
	@GetMapping(value="applyTest01_1")
	public String doTest1_1(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "우울감(PHQ_9)");
		return "healthTest/applyTest01_1";
	}
	@GetMapping(value="applyTest01_2")
	public String doTest1_2(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "스마트폰 과의존 척도");
		return "healthTest/applyTest01_2";
	}
	@GetMapping(value="applyTest01_3")
	public String doTest1_3(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "청소년 도박문제 자가점검 (CAGI)");
		return "healthTest/applyTest01_3";
	}
	@GetMapping(value="applyTest01_4")
	public String doTest1_4(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "범불안장애 척도 (GAD-7)");
		return "healthTest/applyTest01_4";
	}
	@GetMapping(value="applyTest01_5")
	public String doTest1_5(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "스트레스 대처반응척도 (K-CSI)");
		return "healthTest/applyTest01_5";
	}
	@GetMapping(value="applyTest01_6")
	public String doTest1_6(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "우울증 척도 (CES-D)");
		return "healthTest/applyTest01_6";
	}
	@GetMapping(value="applyTest01_7")
	public String doTest1_7(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "스트레스 지각척도 (PSS)");
		return "healthTest/applyTest01_7";
	}
	@GetMapping(value="applyTest01_8")
	public String doTest1_8(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "조기 정신증 검사 (K-ESI)");
		return "healthTest/applyTest01_8";
	}
	@GetMapping(value="applyTest01_9")
	public String doTest1_9(Model model, String majorDiv, String midDiv) {
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", "교정된 정신증 고위험군 선별도구 (mKPQ-16)");
		return "healthTest/applyTest01_9";
	}
	@GetMapping(value="applyTest02_1")
	public String doTest2_1(Model model, String majorDiv) {
		model.addAttribute("major_div",majorDiv);
		model.addAttribute("mid_div", "우울, 불안할때");
		return "healthTest/applyTest02_1";
	}
	@GetMapping(value="applyTest02_2")
	public String doTest2_2(Model model, String majorDiv) {
		model.addAttribute("major_div",majorDiv);
		model.addAttribute("mid_div", "잠을 못잘때");
		return "healthTest/applyTest02_2";
	}
	@GetMapping(value="applyTest02_3")
	public String doTest2_3(Model model, String majorDiv) {
		model.addAttribute("major_div",majorDiv);
		model.addAttribute("mid_div", "내가 느끼는 감정·행동이 낯설게 느껴질때");
		return "healthTest/applyTest02_3";
	}
	
	@PostMapping(value="testResult")
	public String sendResult(Long testSum,String testStat, Model model,  String majorDiv, String midDiv, String minorDiv, String totalStat) {
		System.out.println(majorDiv);
		System.out.println(midDiv);
		System.out.println(totalStat);
		System.out.println(minorDiv);
		model.addAttribute("major_div", majorDiv);
		model.addAttribute("mid_div", midDiv);
		model.addAttribute("minor_div", minorDiv);
		model.addAttribute("testSum", testSum);
		model.addAttribute("testStat", testStat);
		model.addAttribute("totalStat",totalStat);
		model.addAttribute("mhTestResultDto", new MhTestResultDto());
		
		return "healthTest/testResult";
	}
//	@PostMapping(value="newResult")
//	public String saveResult(Authentication auth, Model model, MhTestResultDto mhTestResultDto) throws Exception {
//		if(auth != null) {
//			User user = (User)auth.getPrincipal();
//			Member member = mhTestResultService.findMember(user.getName());
//			model.addAttribute("errorMessage", "회원만 저장이 가능합니다. 저장을 하려면 로그인 또는 회원가입을 해주세요.");
//			
//		}else {
//		System.out.println(mhTestResultDto);
//		mhTestResultService.saveResult(mhTestResultDto);
//		}
//		
//		return "healthTest/testResult";
//	}	
	@PostMapping(value="newResult")
	@ResponseBody
	public void saveResult(@RequestParam(value="major_div") String major_div, @RequestParam(value="mid_div") String mid_div, 
			@RequestParam(value="minor_div") String minor_div, @RequestParam(value="result_content") String result_content, Model model, Authentication auth) throws Exception {
		
		
		MhTestResultDto mhTestResult = new MhTestResultDto();
		mhTestResult.setMajor_div(major_div);
		mhTestResult.setMid_div(mid_div);
		mhTestResult.setMinor_div(minor_div);
		mhTestResult.setResult_content(result_content);
		mhTestResultService.saveResult(mhTestResult, auth.getName());
	}
	
}
