package com.weepl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/healthTest")
@RequiredArgsConstructor
public class HealthTestController {
	
	@GetMapping(value="testMain")
	public String TestMain() {
		return "healthTest/healthTestMain";
	}
	
	
	
	@GetMapping(value="applyTest01")
	public String Test01() {
		return "healthTest/applyTest01";
	}
	@GetMapping(value="applyTest02")
	public String Test02() {
		return "healthTest/applyTest02";
	}
	@GetMapping(value="applyTest01_1")
	public String doTest1_1() {
		return "healthTest/applyTest01_1";
	}
	@GetMapping(value="applyTest01_2")
	public String doTest1_2() {
		return "healthTest/applyTest01_2";
	}
	@GetMapping(value="applyTest01_3")
	public String doTest1_3() {
		return "healthTest/applyTest01_3";
	}
	@GetMapping(value="applyTest01_4")
	public String doTest1_4() {
		return "healthTest/applyTest01_4";
	}
	@GetMapping(value="applyTest01_5")
	public String doTest1_5() {
		return "healthTest/applyTest01_5";
	}
	@GetMapping(value="applyTest01_6")
	public String doTest1_6() {
		return "healthTest/applyTest01_6";
	}
	@GetMapping(value="applyTest01_7")
	public String doTest1_7() {
		return "healthTest/applyTest01_7";
	}
	@GetMapping(value="applyTest01_8")
	public String doTest1_8() {
		return "healthTest/applyTest01_8";
	}
	@GetMapping(value="applyTest01_9")
	public String doTest1_9() {
		return "healthTest/applyTest01_9";
	}
	@GetMapping(value="applyTest02_1")
	public String doTest2_1() {
		return "healthTest/applyTest02_1";
	}
	@GetMapping(value="applyTest02_2")
	public String doTest2_2() {
		return "healthTest/applyTest02_2";
	}
	@GetMapping(value="applyTest02_3")
	public String doTest2_3() {
		return "healthTest/applyTest02_3";
	}
	
	@PostMapping(value="testResult")
	public String sendResult(String testName, Long testSum,String testStat, Model model) {
		System.out.println(testName);
		System.out.println(testSum);
		model.addAttribute("testName", testName);
		model.addAttribute("testSum", testSum);
		model.addAttribute("testStat", testStat);
		return "healthTest/testResult";
	}
	
}
