package com.weepl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weepl.dto.MhinfoDto;
import com.weepl.dto.NoticeDto;
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
	
	@GetMapping("/")
	public String main(Model model) {
	    List<NoticeDto> noticeList = noticeService.getNoticeList();
	    List<MhinfoDto> mhinfoList = mhinfoService.getMhinfoList();
	    model.addAttribute("noticeList", noticeList);
	    model.addAttribute("mhinfoList", mhinfoList);
	    System.out.println("skjdnvsjkdfsdfㄴ이라ㅓㄴ디ㅑ러냐ㅣㄷ러"+mhinfoList);
	    return "main";
	}
}
