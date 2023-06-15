package com.weepl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping(value = "/")
	public String main(Model model) {
		return "main";
	}
	
	@GetMapping(value = "/siteMap")
	public String siteMap(){
		return "/etc/siteMap";
	}
	
	@GetMapping(value = "/weeProjectInfo")
	public String weeProjectInfo(){
		return "/etc/weeProjectInfo";
	}
	
	@GetMapping(value = "/weeSymbol")
	public String weeSymbol(){
		return "/etc/weeSymbol";
	}
}
