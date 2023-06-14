package com.weepl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weepl.dto.MhinfoDto;
import com.weepl.dto.NoticeDto;
import com.weepl.service.MhinfoService;
import com.weepl.service.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	private final NoticeService noticeService;
	private final MhinfoService mhinfoService;
	
	@GetMapping("/")
	public String main(Model model) {
	    List<NoticeDto> noticeList = noticeService.getNoticeList();
	    List<MhinfoDto> mhinfoList = mhinfoService.getMhinfoList();
	    model.addAttribute("noticeList", noticeList);
	    System.out.println("sdflknsdlfinsdlif"+noticeList);
	    model.addAttribute("mhinfoList", mhinfoList);
	    System.out.println("skjdnvsjkdfsdfㄴ이라ㅓㄴ디ㅑ러냐ㅣㄷ러"+mhinfoList);
	    return "main";
	}
}
