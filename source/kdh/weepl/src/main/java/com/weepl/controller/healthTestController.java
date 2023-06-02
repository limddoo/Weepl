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
	
	@PostMapping(value="testResult")
	public String sendResult(String testName, Long testSum, Model model) {
		System.out.println(testName);
		System.out.println(testSum);
		model.addAttribute("testName", testName);
		model.addAttribute("testSum", testSum);
		return "healthTest/testResult";
	}
	
}
