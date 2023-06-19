package com.weepl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weepl.entity.Mhinfo;
import com.weepl.entity.Notice;
import com.weepl.service.MhinfoService;
import com.weepl.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	@Autowired
	private final NoticeService noticeService;
	@Autowired
	private final MhinfoService mhinfoService;
	
	@GetMapping(value = "/")
	public String main(Model model) {
		List<Notice> noticeList = noticeService.getNoticeList();
	    List<Mhinfo> mhinfoList = mhinfoService.getMhinfoList();
	    model.addAttribute("noticeList", noticeList.subList(0, 5));
	    model.addAttribute("mhinfoList", mhinfoList.subList(0, 5));
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
