package com.weepl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ReserveApplyDto;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;
import com.weepl.entity.ReserveSchedule;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.ReserveApplyRepository;
import com.weepl.repository.ReserveScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveApplyService {

	private final Logger LOGGER = LoggerFactory.getLogger(ReserveApplyService.class);

	private final ReserveApplyRepository reserveApplyRepository;
	private final ReserveScheduleRepository reserveScheduleRepository;
	private final MemberRepository memberRepository;

	public Long saveReserveApply(ReserveApplyDto reserveApplyDto) {
		LOGGER.info("reserveApplyDto의 값:{}",reserveApplyDto);
		ReserveApply reserveApply = reserveApplyDto.reserveApplyDtoToReserveApply();
		reserveApply.setMemberCd(memberRepository.findById(reserveApplyDto.getName()));

		reserveApplyRepository.save(reserveApply);
		
		ReserveSchedule reserveSchedule = reserveScheduleRepository.findById(reserveApplyDto.getReserveScheduleCd())
				.orElseThrow(EntityNotFoundException::new);
		reserveSchedule.updateReserveSchedule(reserveApplyDto); 
		
		return reserveApply.getReserveApplyCd();
	}

	public List<Map<String, Object>> addReserveApply() {
		
		List<Map<String, Object>> reserveApplyList = new ArrayList<Map<String, Object>>();

		List reserveScheduleList = reserveScheduleRepository.findAll();

		for (int i = 0; i < reserveScheduleList.size(); i++) {
			Map<String, Object> reserveApply = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			ObjectMapper objectMapper = new ObjectMapper();
			Map result = objectMapper.convertValue(reserveScheduleList.get(i), Map.class);
			

			sb.append(result.get("reserveDate"));
			sb.append("T");
			sb.append(result.get("reserveTime"));

			reserveApply.put("id", result.get("cd"));
			reserveApply.put("title", result.get("status"));
			reserveApply.put("start", sb);
			if(result.get("status").equals("예약완료")) {
				reserveApply.put("color", "red");
				//reserveApply.put("title", "예약완료");
			}
			sb = null;
			result.clear();
			reserveApplyList.add(reserveApply);
		}
		return reserveApplyList;
	}
	
	
	public List<Map<String, Object>> viewMyReservation(String name) {
		//1. member repository findbyid 해서 membercd 조회
		//2. memberCd로 reserveAplly 조회
		//3. reserveapply의 reserveschedulecd 값들 조회
		//4. reserveschedule에서 해당 reserveCd값으로 조회 후 결과물 받기
		Member member = memberRepository.findById(name);
		ReserveApply reserveApply = reserveApplyRepository.findByMemberCd(member); 
		Long reserveScheduleCd = reserveApply.getReserveSchedule();
		reserveScheduleRepository.findById();
		List<Map<String, Object>> reserveApplyList = new ArrayList<Map<String, Object>>();

		List reserveScheduleList = reserveScheduleRepository.findAll();

		LOGGER.info("reserveScheduleList의 값 : {}", reserveScheduleList);
		for (int i = 0; i < reserveScheduleList.size(); i++) {
			Map<String, Object> reserveApply = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			ObjectMapper objectMapper = new ObjectMapper();
			Map result = objectMapper.convertValue(reserveScheduleList.get(i), Map.class);
			

			sb.append(result.get("reserveDate"));
			sb.append("T");
			sb.append(result.get("reserveTime"));

			reserveApply.put("id", result.get("cd"));
			reserveApply.put("title", result.get("status"));
			reserveApply.put("start", sb);
			if(result.get("status").equals("예약완료")) {
				reserveApply.put("color", "red");
				//reserveApply.put("title", "예약완료");
			}
			sb = null;
			result.clear();
			reserveApplyList.add(reserveApply);
		}
		return reserveApplyList;
	}
	
	
	@Transactional(readOnly=true)
	public ReserveApplyDto getReserveDtl(Long reserveScheduleCd) {
		ReserveApply reserveApply = reserveApplyRepository.findByReserveScheduleCd(reserveScheduleCd);
		ReserveApplyDto reserveApplyDto = new ReserveApplyDto();
		if(reserveApply!=null) {  
			reserveApplyDto = ReserveApplyDto.reserveApplyToReserveApplyDto(reserveApply);
			reserveApplyDto.setMemCd(reserveApply.getMemberCd().getCd());
			reserveApplyDto.setId(reserveApply.getMemberCd().getId()); //컨트롤러에서 현재 로그인한 사용자 ID와 비교하기 위함
		}
		return reserveApplyDto;
	}

}
