package com.weepl.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.ReserveApplyDto;
import com.weepl.service.ReserveApplyService;

@Controller
@RequestMapping("/reservation")
public class ReserveApplyController {

	private final Logger LOGGER = LoggerFactory.getLogger(ReserveApplyController.class);

	@Autowired
	ReserveApplyService reserveApplyService;

	@RequestMapping
	public String showReservation(Model model) {
		model.addAttribute("reserveApplyDto", new ReserveApplyDto());
		return "/reservation/reservation";
	}

	//관리자가 예약 일정 추가
	@ResponseBody
	@GetMapping("/selectDate") // ajax 데이터 전송 URL //
	public  List<Map<String, Object>> addReservation() { 
		LOGGER.info(reserveApplyService.addReserveApply().toString());
		return	reserveApplyService.addReserveApply();  
	}
	
	//사용자가 예약 일정 선택

	@ResponseBody
	@PostMapping("/selectReservation")
	public void selectReservation( @RequestBody Map<String, Object> selectedReservation)throws Exception{
		try{
		LOGGER.info("RequestBody의 내용 {}", selectedReservation);
		LOGGER.info("selectReservation method 호출");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
