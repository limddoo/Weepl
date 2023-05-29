package com.weepl.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.weepl.dto.MainMhinfoDto;
import com.weepl.dto.MhinfoSearchDto;
import com.weepl.service.MhinfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	@GetMapping(value = "/")
	public String main(Model model) {
	
		

		
		return "main";
	}
}
