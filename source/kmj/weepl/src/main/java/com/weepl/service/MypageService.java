package com.weepl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.dto.MypageFormDto;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveSchedule;
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
	
	public List viewMyReservationList(String name) {
		Member member = memberRepository.findById(name);
		Long memberCd = member.getCd();	
		List foundReserveApply = reserveApplyRepository.findByMember(memberCd);
		return foundReserveApply;
	}
	
	
	public List<Map<String, Object>> viewMyReservationCalendar(String name) {
		//1. member repository findbyid 해서 membercd 조회
		//2. memberCd로 reserveAplly 조회
		//3. reserveapply의 reserveschedulecd 값들 조회
		//4. reserveschedule에서 해당 reserveCd값으로 조회 후 결과물 받기
		Member member = memberRepository.findById(name);
		Long memberCd = member.getCd();	
		List foundReserveApply = reserveApplyRepository.findByMember(memberCd);			
		List<Map<String, Object>> reserveApplyList = new ArrayList<Map<String, Object>>();
		
		for(int i =0; i<foundReserveApply.size(); i++) {
			Map<String, Object> reserveApply = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder();
			ObjectMapper objectMapper2 = new ObjectMapper();
			Map result2 = objectMapper2.registerModule(new JavaTimeModule()).convertValue(foundReserveApply.get(i), Map.class);
			Map result3 = objectMapper2.registerModule(new JavaTimeModule()).convertValue(result2.get("reserveSchedule"), Map.class);

			sb.append(result3.get("reserveDate"));
			sb.append("T");
			sb.append(result3.get("reserveTime"));
			
			reserveApply.put("id", result3.get("cd"));
			reserveApply.put("title", result3.get("status"));
			reserveApply.put("start", sb);
			if(result3.get("status").equals("예약완료")) {
				reserveApply.put("color", "red");
				//reserveApply.put("title", "예약완료");
			}
			sb = null;
			result3.clear();
			reserveApplyList.add(reserveApply);
		}
		
		return reserveApplyList;
	}

	public void deleteReserveScedult(Long id) {
		reserveScheduleRepository.deleteById(id);	
	}
	
	public void deleteUserReserveScedult(Long id) {
		reserveApplyRepository.deleteReserveApply(id);
		
		ReserveSchedule reserveSchedule = reserveScheduleRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
		reserveSchedule.updateReserveSchedule(id, "예약가능");	
	}
	
}
