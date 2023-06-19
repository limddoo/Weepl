package com.weepl.controller;

import java.util.Collections;
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

		// 최신순으로 정렬
		Collections.reverse(noticeList);
		Collections.reverse(mhinfoList);
		// 리스트 내용물이 5개 미만일때
		if (noticeList.size() < 5) {
			model.addAttribute("noticeList", noticeList);
		} else {
			// 리스트의 가장 마지막 5개 추출해서 최신순으로 정렬
			model.addAttribute("noticeList", noticeList.subList(0, 5));
		}
		if (mhinfoList.size() < 5) {
			model.addAttribute("mhinfoList", mhinfoList);
		} else {
			model.addAttribute("mhinfoList", noticeList.subList(0, 5));
		}
		return "main";
	}

	@GetMapping(value = "/siteMap")
	public String siteMap() {
		return "/etc/siteMap";
	}

	@GetMapping(value = "/weeProjectInfo")
	public String weeProjectInfo() {
		return "/etc/weeProjectInfo";
	}

	@GetMapping(value = "/weeSymbol")
	public String weeSymbol() {
		return "/etc/weeSymbol";
	}
}
