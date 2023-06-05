package com.weepl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ReserveApplyDto;
import com.weepl.service.ReserveApplyService;

@Controller
@RequestMapping("/reservation")
public class ReserveApplyController {

	private final Logger LOGGER = LoggerFactory.getLogger(ReserveApplyController.class);

	@Autowired
	ReserveApplyService reserveApplyService;

	@GetMapping("/main")
	public String showReservation(Model model) {
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
	public void selectReservation(@RequestBody Map<String, Object> selectedReservation, Model model)throws Exception{
		try{
		LOGGER.info("RequestBody의 내용 {}", selectedReservation);
		LOGGER.info("selectReservation method 호출");
		model.addAttribute("selectedReservation",selectedReservation);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

	
	//showPopUp() 컨트롤러에서 실행		
	  @RequestMapping("/reservationForm/{data}") 
	  public String	reservationForm(@PathVariable("data") String jsonData, Model model) throws Exception {
	  
	  LOGGER.info("reservationForm method 호출");
	  //LOGGER.info("reservationForm Model의 값 : {}", jsonData);
	  ReserveApplyDto reserveApplyDto = new ReserveApplyDto();
	  Map<String, Object> mappedJsonData = jsonToMap(jsonData);
	  //LOGGER.info("mappedJsonData의 값 : {}",mappedJsonData.get("id").toString());
	  reserveApplyDto.setReserveStatus("예약완료");
	  reserveApplyDto.setReserveId(mappedJsonData.get("id").toString());
	  reserveApplyDto.setName(mappedJsonData.get("name").toString());
	  
	  model.addAttribute("reserveApplyDto", reserveApplyDto);
	  
	 // LOGGER.info(jsonData);
	  
	  return "/reservation/reservationForm"; 
	  }
	 
	
	@RequestMapping("/applyReservation")
	public String applyReservation(@RequestBody Map<String, Object> reserveApplyInfo, BindingResult bindingResult, Model model) throws Exception {
		LOGGER.info("applyReservation 호출!");
		//LOGGER.info("data의 값{}",data);
		LOGGER.info("jsonData의 값{}",reserveApplyInfo);
		ReserveApplyDto reserveApplyDto = new ReserveApplyDto();
		reserveApplyDto.setReserveStatus(reserveApplyInfo.get("reserveStatus").toString());
		reserveApplyDto.setReserveId(reserveApplyInfo.get("reserveId").toString());
		reserveApplyDto.setName(reserveApplyInfo.get("name").toString());
		reserveApplyDto.setReserveTitle(reserveApplyInfo.get("reserveTitle").toString());
		reserveApplyDto.setConsReqContent(reserveApplyInfo.get("consReqContent").toString());
		LOGGER.info("reserveApplyDto의 값 : {}",reserveApplyDto.toString());
		if (bindingResult.hasErrors()) {  //필수값이 없다면 다시 예약등록 페이지로 이동
			return "reservation/reservationForm";
		}
		
		try {
			reserveApplyService.saveReserveApply(reserveApplyDto);
		} 
		
		catch(Exception e) {
			model.addAttribute("errorMessage", "상담예약 등록중 에러가 발생하였습니다.");
			e.printStackTrace();
			return "reservation/reservationForm";
		}
		return "redirect:/reservation/main";
	}
	
	
	public Map<String, Object> jsonToMap(String jsonData) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mappedJsonData = mapper.readValue(jsonData, Map.class);
        return mappedJsonData;
    }
	
	
}
