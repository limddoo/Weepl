package com.weepl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.dto.MypageFormDto;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.ReserveApplyRepository;
import com.weepl.repository.ReserveScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
	private final MemberRepository memberRepository;
	private final ReserveApplyRepository reserveApplyRepository;
	private final ReserveScheduleRepository reserveScheduleRepository;
	
	private final Logger LOGGER = LoggerFactory.getLogger(MypageService.class);
	
	public Member findMember(String id) {
		return memberRepository.findById(id);
	}

	public void updateMember(MypageFormDto mypageFormDto) {
		Member member = memberRepository.findById(mypageFormDto.getId());
		validateDuplicateMember(member);
		member.updateMember(mypageFormDto);
	}
	
	public void updateMember(ModMemberInfoDto modMemberInfoDto) {
		Member member = memberRepository.findById(modMemberInfoDto.getId());
		validateDuplicateMember(member);
		member.updateMember(modMemberInfoDto);
	}
	
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 이메일입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
	}

	public void updateMemberPwd(String id, String pwd, PasswordEncoder passwordEncoder) {
		Member member = memberRepository.findById(id);
		member.updateMemberPwd(pwd, passwordEncoder);
	}
	
	public void quitMember(String id) {
		Member member = memberRepository.findById(id);
		member.quitMember();
	}
	
	public List<Map<String, Object>> viewMyReservation(String name) {
		//1. member repository findbyid 해서 membercd 조회
		//2. memberCd로 reserveAplly 조회
		//3. reserveapply의 reserveschedulecd 값들 조회
		//4. reserveschedule에서 해당 reserveCd값으로 조회 후 결과물 받기
		Member member = memberRepository.findById(name);
		ReserveApply foundReserveApply = reserveApplyRepository.findByMember(member); 
		Long reserveScheduleCd = foundReserveApply.getReserveSchedule().getCd();
		reserveScheduleRepository.findById(reserveScheduleCd);
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
	
}
