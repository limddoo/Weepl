package com.weepl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.dto.MyConsDtlDto;
import com.weepl.dto.MyConsDto;
import com.weepl.dto.MypageFormDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveSchedule;
import com.weepl.repository.BoardConsRepository;
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
private final BoardConsRepository boardConsRepository;
	private BoardCons myCons;
	
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
		if(findMember.getCd() != member.getCd()) {
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
	
@Transactional(readOnly = true)
	public Page<MyConsDto> getMyConsPage(Pageable pageable, String memberId) {
		Map<String, Object> result = boardConsRepository.getMyBoardConsList(pageable, memberId);
		List<BoardCons> myConsList = (List) result.get("content");
		List<MyConsDto> myConsDtoList = new ArrayList<>();
		System.out.println(result);
		System.out.println(myConsList);
		
		for (BoardCons myCons : myConsList) {
			MyConsDto myConsDto = createMyConsListDto(myCons);
			myConsDtoList.add(myConsDto);
		}
		System.out.println(myConsDtoList);
		return new PageImpl<>(myConsDtoList, pageable, (Long) result.get("total"));
	}

	public MyConsDto createMyConsListDto(BoardCons boardCons) {
		MyConsDto myConsDto = new MyConsDto();
		myConsDto.setCd(boardCons.getCd());
		myConsDto.setTitle(boardCons.getTitle());
		myConsDto.setReg_dt(boardCons.getRegDt());
		myConsDto.setRes_yn(boardCons.getRes_yn());
		
		return myConsDto;
	}
	
	public MyConsDtlDto getConsDtl(Long myConsCd) {
	    // 상세 정보 조회 로직 구현
	    // 예시로 임의의 상세 정보를 생성하여 반환하도록 구현합니다.
	    MyConsDtlDto consDtl = new MyConsDtlDto();
	    consDtl.setCd(myConsCd);
	    consDtl.setTitle("상담 제목");
	    consDtl.setContent("상담 내용");
	    // 상세 정보에 대한 필드 값을 설정합니다.

	    return consDtl;
	}
	
	// 닉네임 중복여부 true: 사용가능, false: 사용불가
	public String setNickName(String nickName, String userId) {
		Member member = memberRepository.findByNickName(nickName);
		if(member == null) {
			Member foundMember = memberRepository.findById(userId);
			foundMember.updateMemberNickName(nickName);
			return nickName;
		} else {
			return null;
		}
	}
}
