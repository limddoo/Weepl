package com.weepl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.dto.MhinfoFormDto;
import com.weepl.dto.SearchDto;
import com.weepl.entity.Mhinfo;
import com.weepl.service.BoardImgService;
import com.weepl.service.MhinfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mhinfo")
@RequiredArgsConstructor
public class MhinfoController {

	private final MhinfoService mhinfoService;
	private final BoardImgService boardImgService;
	
	@Autowired
	private HttpServletRequest request;

	@GetMapping(value = "/admin/mhinfo/new")
	public String mhinfoForm(Model model) {
		model.addAttribute("mhinfoFormDto", new MhinfoFormDto("SCHOOL"));
		return "mhinfo/mhinfoForm";
	}

	@PostMapping(value = "/admin/mhinfo/new")
	public String mhinfoNew(@Valid MhinfoFormDto mhinfoFormDto, BindingResult bindingResult, Model model,
			@RequestParam("boardImgFile") List<MultipartFile> boardImgFileList) {

		if (bindingResult.hasErrors()) {
			return "mhinfo/mhinfoForm";
		}

		if (boardImgFileList.get(0).isEmpty() && mhinfoFormDto.getCd() == null) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
			return "mhinfo/mhinfoForm";
		}

		try {
			mhinfoService.saveMhinfo(mhinfoFormDto, boardImgFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "글 등록 중 에러가 발생하였습니다.");
			return "mhinfo/mhinfoForm";
		}
		return "redirect:/mhinfo/mhinfos";
	}

	@PostMapping(value = "/admin/mhinfo/{mhinfoCd}")
	public String mhhinfoUpdate(@Valid MhinfoFormDto mhinfoFormDto, BindingResult bindingResult,
	        @RequestParam("boardImgFile") List<MultipartFile> boardImgFileList, 
	        @RequestParam("mhinfoCate") String mhinfoCate,
	        Model model, HttpServletRequest request) {
	    if (bindingResult.hasErrors()) {
	        return "mhinfo/mhinfoForm";
	    }

	    if (boardImgFileList.get(0).isEmpty() && mhinfoFormDto.getCd() == null) {
	        model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값입니다.");
	        return "mhinfo/mhinfoForm";
	    }

	    try {
	        mhinfoFormDto.setMhinfoCate(mhinfoCate); // 카테고리 값을 설정
	        mhinfoService.updateMhinfo(mhinfoFormDto, boardImgFileList);
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "글수정 중 에러가 발생하였습니다.");
	        return "mhinfo/mhinfoForm";
	    }

	    return "redirect:/mhinfo/mhinfos";
	}
	
	
	@GetMapping(value = { "/mhinfos", "/mhinfos/{page}" })
	public String mhinfoMng(SearchDto mhinfoSearchDto, @PathVariable("page") Optional<Integer> page,
			Model model) {
		int pageNumber = page.orElse(0);
		
		if(pageNumber < 0) {
			pageNumber = 0;
		}
		
		Pageable pageable = PageRequest.of(pageNumber, 6);
		Page<Mhinfo> mhinfos = mhinfoService.getMhinfoPage(mhinfoSearchDto, pageable);

		// 마지막 페이지인 경우에 대한 처리
		if (pageNumber >= mhinfos.getTotalPages() && mhinfos.getTotalPages() > 0) {
			// 현재 페이지를 마지막 페이지로 설정
			pageNumber = mhinfos.getTotalPages() - 1;
			// 다시 페이지 요청
			pageable = PageRequest.of(pageNumber, 6);
			mhinfos = mhinfoService.getMhinfoPage(mhinfoSearchDto, pageable);
		}
		model.addAttribute("mhinfos", mhinfos);
		model.addAttribute("mhinfoSearchDto", mhinfoSearchDto);
		model.addAttribute("maxPage", 5);
		return "mhinfo/mhinfoMng";
	}
	
	
	
	@GetMapping(value = "/admin/mhinfo/{mhinfoCd}")
	public String mhinfoDtl(@PathVariable("mhinfoCd") Long mhinfoCd, Model model) {
		try {
			MhinfoFormDto mhinfoFormDto = mhinfoService.getMhinfoDtl(mhinfoCd);
			model.addAttribute("mhinfoFormDto", mhinfoFormDto);
		} catch (EntityNotFoundException e) {
			model.addAttribute("errormessage", "존재하지 않는 게시글입니다.");
			model.addAttribute("mhinfoFormDto", new MhinfoFormDto());
			return "mhinfo/mhinfoForm";
		}
		return "mhinfo/mhinfoForm";
	}

	@GetMapping(value = "/mhinfo/{mhinfoCd}")
	public String mhinfoDtl(Model model, @PathVariable("mhinfoCd") Long mhinfoCd) {
		MhinfoFormDto mhinfoFormDto = mhinfoService.getMhinfoDtl(mhinfoCd);
		mhinfoService.updateView(mhinfoCd);
		model.addAttribute("mhinfo", mhinfoFormDto);
		return "mhinfo/mhinfoDtl";
	}

	@GetMapping("/admin/delete/{mhinfoCd}")
	public String deleteMhinfo(@PathVariable Long mhinfoCd, Model model) throws Exception{		
		boardImgService.deleteBoardImg(mhinfoCd);
		mhinfoService.deleteMhinfo(mhinfoCd);
		return "redirect:/mhinfo/mhinfos";
	}
	
	@GetMapping(value = "/likes")
	@ResponseBody
	public HashMap<String, Integer> mhinfoLikes(Model model, Long mhinfoCd) {
		HashMap<String, Integer> map = new HashMap<>();
		mhinfoService.updateLikes(mhinfoCd);
		MhinfoFormDto dto = mhinfoService.getMhinfoDtl(mhinfoCd);
		map.put("result", dto.getLikeCnt());
		
		return map;
	}
}
