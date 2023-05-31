package com.weepl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value="/healthTest")
@RequiredArgsConstructor
public class healthTestController {
	
	@GetMapping(value="testMain")
	public String TestMain() {
		return "healthTest/healthTestMain";
	}
	
	
	
	@GetMapping(value="applyTest01")
	public String Test01() {
		return "healthTest/applyTest01";
	}
	@GetMapping(value="applyTest01_1")
	public String doTest() {
		return "healthTest/applyTest01_1";
	}
	@GetMapping(value="testResult")
	public String showResult() {
		return "healthTest/testReslut";
	}
}
