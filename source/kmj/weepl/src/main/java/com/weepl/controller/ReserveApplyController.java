package com.weepl.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ReserveApplyDto;
import com.weepl.entity.ReserveSchedule;
import com.weepl.service.ReserveApplyService;

@Controller
@RequestMapping("/reservation")
public class ReserveApplyController {

	private final Logger LOGGER = LoggerFactory.getLogger(ReserveApplyController.class);

	@Autowired
	ReserveApplyService reserveApplyService;

	@GetMapping("/main")
	public String showReservation() {
		return "/reservation/reservation";
	}

	// 관리자가 예약 일정 추가
	@ResponseBody
	@GetMapping("/selectDate") // ajax 데이터 전송 URL //
	public List<Map<String, Object>> addReservation() {
		return reserveApplyService.addReserveApply();
	}

	// 사용자가 예약 일정 선택
	@ResponseBody
	@PostMapping("/selectReservation")
	public void selectReservation(@RequestBody Map<String, Object> selectedReservation, Model model,
			Principal principal) throws Exception {

		String id = principal.getName();
		LOGGER.info("id={}", id);
		try {
			LOGGER.info("selectedReservation : {}", selectedReservation.toString());
			model.addAttribute("selectedReservation", selectedReservation);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// showPopUp() 컨트롤러에서 실행
	@RequestMapping("/reservationForm/{data}")
    public String reservationForm(@PathVariable("data") String jsonData, Model model, Principal principal) throws IOException {

		Map<String, Object> mappedJsonData = jsonToMap(jsonData);
		LOGGER.info("json데이터: {}", mappedJsonData.toString());
		ReserveApplyDto reserveApplyDto = reserveApplyService
				.getReserveDtl(Long.parseLong(mappedJsonData.get("id").toString()));

		if (reserveApplyDto.getId() == null) // 신규에약
		{
			reserveApplyDto.setReserveStatus("예약완료");
			reserveApplyDto.setName(mappedJsonData.get("name").toString());
			reserveApplyDto.setReserveScheduleCd(Long.parseLong(mappedJsonData.get("id").toString()));
			model.addAttribute("reserveApplyDto", reserveApplyDto);
		} else if (reserveApplyDto.getId().equals(principal.getName())) // 내가 예약한 경우
		{
			reserveApplyDto.setReserveStatus("예약완료");
			reserveApplyDto.setName(mappedJsonData.get("name").toString());
			reserveApplyDto.setReserveScheduleCd(Long.parseLong(mappedJsonData.get("id").toString()));
			model.addAttribute("reserveApplyDto", reserveApplyDto);
		} else // 남의 예약을 클릭한 경우
		{
			reserveApplyDto = new ReserveApplyDto();
			//model.addAttribute("errorMessage", "예약이 불가능합니다.");
			model.addAttribute("reserveApplyDto", reserveApplyDto);
		}
		return "/reservation/reservationForm";
	}

	// 상담예약
	@RequestMapping("/applyReservation")
	public String applyReservation(@RequestBody Map<String, Object> reserveApplyInfo, BindingResult bindingResult,
			Model model) throws Exception {
		ReserveApplyDto reserveApplyDto = new ReserveApplyDto();
		reserveApplyDto.setReserveStatus(reserveApplyInfo.get("reserveStatus").toString());
		reserveApplyDto.setReserveScheduleCd(Long.parseLong(reserveApplyInfo.get("reserveScheduleCd").toString()));
		reserveApplyDto.setName(reserveApplyInfo.get("name").toString());
		reserveApplyDto.setReserveTitle(reserveApplyInfo.get("reserveTitle").toString());
		reserveApplyDto.setConsReqContent(reserveApplyInfo.get("consReqContent").toString());

		if (bindingResult.hasErrors()) { // 필수값이 없다면 다시 예약등록 페이지로 이동
			return "reservation/reservationForm";
		}

		try {
			reserveApplyService.saveReserveApply(reserveApplyDto);
		}

		catch (Exception e) {
			model.addAttribute("errorMessage", "상담예약 등록중 에러가 발생하였습니다.");
			e.printStackTrace();
			return "reservation/reservationForm";
		}
		return "redirect:/reservation/main";
	}


	// 상담예약 취소

	@RequestMapping("/cancelReservation/{**}")
	public String cancelReservation() {
		return "asd";
	}

	public Map<String, Object> jsonToMap(String jsonData) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mappedJsonData = mapper.readValue(jsonData, Map.class);
		return mappedJsonData;
	}

}
