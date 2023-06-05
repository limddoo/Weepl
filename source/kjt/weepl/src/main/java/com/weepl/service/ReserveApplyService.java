package com.weepl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ReserveApplyDto;
import com.weepl.entity.ReserveApply;
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

		ReserveApply reserveApply = reserveApplyDto.reserveApplyDtotoReserveApply();
		reserveApply.setMemberCd(memberRepository.findById(reserveApplyDto.getName()));

		reserveApplyRepository.save(reserveApply);

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
			sb = null;
			result.clear();
			reserveApplyList.add(reserveApply);

		}

		return reserveApplyList;
	}

}
