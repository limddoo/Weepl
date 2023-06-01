package com.weepl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.weepl.dto.WeeNetworkFormDto;
import com.weepl.dto.WeeNetworkSearchDto;
import com.weepl.entity.WeeNetwork;
import com.weepl.service.WeeNetworkService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weeNetwork")
public class WeeNetworkController {

	private final WeeNetworkService weeNetworkService;
	
	@GetMapping
	public String weeNetwork(Model model) throws JsonProcessingException {
	    return "weeNetwork/weeNetworkView";
	}

	@GetMapping("/admin/new")
	public String weeNetworkForm(Model model) {
		model.addAttribute("weeNetworkFormDto", new WeeNetworkFormDto());
		return "weeNetwork/weeNetworkForm";
	}
	
	@PostMapping(value = "/admin/new")
	public String weeNetworkNew(@Valid WeeNetworkFormDto weeNetworkFormDto, BindingResult bindingResult, Model model) {
	    if (bindingResult.hasErrors()) {
	        return "weeNetwork/weeNetworkForm";
	    }

	    try {
	        weeNetworkService.saveWeeNetwork(weeNetworkFormDto);
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "센터 등록 중 에러가 발생하였습니다.");
	        return "weeNetwork/weeNetworkForm";
	    }

	    return "redirect:/weeNetwork/search";
	}
	
	@PostMapping(value = "/admin/update/{weeNetworkCd}")
	public String weeNetworkUpdate(@Valid WeeNetworkFormDto weeNetworkFormDto, BindingResult bindingResult, Model model, HttpServletRequest request) {
	    if (bindingResult.hasErrors()) {
	        return "weeNetwork/weeNetworkForm";
	    }

	    try {
	        weeNetworkService.saveWeeNetwork(weeNetworkFormDto);
	    } catch (Exception e) {
	        model.addAttribute("errorMessage", "센터 등록 중 에러가 발생하였습니다.");
	        return "weeNetwork/weeNetworkForm";
	    }

	    return "redirect:/weeNetwork";
	}
	
	 @GetMapping("/getApi")
	    public ResponseEntity<List<WeeNetwork>> getWeeNetworkApi() {
	        List<WeeNetwork> wnList = weeNetworkService.getAllWn();
	        return new ResponseEntity<>(wnList, HttpStatus.OK);
	    }
	 
//	 @GetMapping(value = "/modWeeNetworkInfo/{weeNetworkCd}")
//		public String modWeeNetworkInfoForm(@PathVariable("weeNetworkCd") Long weeNetworkCd, Model model) {
//			WeeNetwork weeNetwork = weeNetworkService.findOne(weeNetworkCd);
//			if (weeNetwork != null) {
//				WeeNetworkFormDto weeNetworkFormDto = WeeNetworkFormDto.of(weeNetwork);
//				model.addAttribute("weeNetworkFormDto", weeNetworkFormDto);
//			} else {
//				// 처리할 로직 추가 (멤버를 찾을 수 없는 경우)
//				model.addAttribute("errorMessage", "멤버를 찾을 수 없습니다."); // 에러 메시지 설정
//				return "redirect:/weeNetwork";
//			}
//			return "member/weeNetworkView";
//		}

	 @PostMapping("/search")
	    public String weeNetworkSearchResult(@ModelAttribute("weeNetworkSearchDto") WeeNetworkSearchDto weeNetworksearchDto, Model model) {
	        List<WeeNetwork> searchResults = weeNetworkService.searchWeeNetworks(weeNetworksearchDto);
	        model.addAttribute("searchResults", searchResults);
	        return "weeNetwork/weeNetworkView";
	    }
	 @GetMapping("/search")
	 public String weeNetworkSearchForm(Model model) {
	     model.addAttribute("weeNetworkSearchDto", new WeeNetworkSearchDto());
	     return "weeNetwork/weeNetworkView";
	 }
	
}