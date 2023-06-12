package com.weepl.controller;

import java.util.List;

import javax.annotation.PostConstruct;
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
import com.weepl.constant.Role;
import com.weepl.dto.MemberFormDto;
import com.weepl.dto.WeeNetworkFormDto;
import com.weepl.dto.WeeNetworkSearchDto;
import com.weepl.entity.Member;
import com.weepl.entity.WeeNetwork;
import com.weepl.service.WeeNetworkService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/weeNetwork")
public class WeeNetworkController {

	private final WeeNetworkService weeNetworkService;
	
	@PostConstruct
	private void createAgency() {
		
		WeeNetworkFormDto weeNetworkFormDto = WeeNetworkFormDto.createAgencySeoul1();
		WeeNetwork weeNetwork = WeeNetwork.createAgency(weeNetworkFormDto);
		weeNetworkService.saveAgency(weeNetwork);
		
		WeeNetworkFormDto weeNetworkFormDto2 = WeeNetworkFormDto.createAgencySeoul2();
		WeeNetwork weeNetwork2 = WeeNetwork.createAgency(weeNetworkFormDto2);
		weeNetworkService.saveAgency(weeNetwork2);
		
		WeeNetworkFormDto weeNetworkFormDto3 = WeeNetworkFormDto.createAgencySeoul3();
		WeeNetwork weeNetwork3 = WeeNetwork.createAgency(weeNetworkFormDto3);
		weeNetworkService.saveAgency(weeNetwork3);
		
		WeeNetworkFormDto weeNetworkFormDto4 = WeeNetworkFormDto.createAgencySeoul4();
		WeeNetwork weeNetwork4 = WeeNetwork.createAgency(weeNetworkFormDto4);
		weeNetworkService.saveAgency(weeNetwork4);
		
		WeeNetworkFormDto weeNetworkFormDto5 = WeeNetworkFormDto.createAgencySeoul5();
		WeeNetwork weeNetwork5 = WeeNetwork.createAgency(weeNetworkFormDto5);
		weeNetworkService.saveAgency(weeNetwork5);
		
		WeeNetworkFormDto weeNetworkFormDto6 = WeeNetworkFormDto.createAgencyDaejeon();
		WeeNetwork weeNetwork6 = WeeNetwork.createAgency(weeNetworkFormDto6);
		weeNetworkService.saveAgency(weeNetwork6);
		
		WeeNetworkFormDto weeNetworkFormDto7 = WeeNetworkFormDto.createAgencyDaejeon2();
		WeeNetwork weeNetwork7 = WeeNetwork.createAgency(weeNetworkFormDto7);
		weeNetworkService.saveAgency(weeNetwork7);
		
		WeeNetworkFormDto weeNetworkFormDto8 = WeeNetworkFormDto.createAgencyDaejeon3();
		WeeNetwork weeNetwork8 = WeeNetwork.createAgency(weeNetworkFormDto8);
		weeNetworkService.saveAgency(weeNetwork8);
		
		WeeNetworkFormDto weeNetworkFormDto9 = WeeNetworkFormDto.createAgencyDaegu();
		WeeNetwork weeNetwork9 = WeeNetwork.createAgency(weeNetworkFormDto9);
		weeNetworkService.saveAgency(weeNetwork9);
		
		WeeNetworkFormDto weeNetworkFormDto10 = WeeNetworkFormDto.createAgencyDaegu2();
		WeeNetwork weeNetwork10 = WeeNetwork.createAgency(weeNetworkFormDto10);
		weeNetworkService.saveAgency(weeNetwork10);
		
		WeeNetworkFormDto weeNetworkFormDto11 = WeeNetworkFormDto.createAgencyDaegu3();
		WeeNetwork weeNetwork11 = WeeNetwork.createAgency(weeNetworkFormDto11);
		weeNetworkService.saveAgency(weeNetwork11);
		
		WeeNetworkFormDto weeNetworkFormDto12 = WeeNetworkFormDto.createAgencyBusan();
		WeeNetwork weeNetwork12 = WeeNetwork.createAgency(weeNetworkFormDto12);
		weeNetworkService.saveAgency(weeNetwork12);
		
		WeeNetworkFormDto weeNetworkFormDto13 = WeeNetworkFormDto.createAgencyBusan2();
		WeeNetwork weeNetwork13 = WeeNetwork.createAgency(weeNetworkFormDto13);
		weeNetworkService.saveAgency(weeNetwork13);
		
		WeeNetworkFormDto weeNetworkFormDto14 = WeeNetworkFormDto.createAgencyBusan3();
		WeeNetwork weeNetwork14 = WeeNetwork.createAgency(weeNetworkFormDto14);
		weeNetworkService.saveAgency(weeNetwork14);
	}
	
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