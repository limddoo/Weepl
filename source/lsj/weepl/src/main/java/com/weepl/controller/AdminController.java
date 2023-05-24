package com.weepl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {
	@GetMapping("/untactConsForm")
    public String untactConsForm() {
    	return "admin/untactCons";
    }
	
	@GetMapping("/chattingForm")
    public String chattingForm() {
    	return "admin/chatting";
    }
}
