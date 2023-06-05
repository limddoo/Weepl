package com.weepl.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.constant.MemberStatus;
import com.weepl.constant.RestrictStatus;
import com.weepl.dto.MemberSearchDto;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.entity.Member;
import com.weepl.entity.MemberRestrict;
import com.weepl.entity.ReserveApply;
import com.weepl.entity.ReserveSchedule;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.MemberRestrictRepository;
import com.weepl.repository.ReserveApplyRepository;
import com.weepl.repository.ReserveScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

	private final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

	private final MemberRepository memberRepository;

	private final MemberRestrictRepository memberRestrictRepository;

	private final ReserveScheduleRepository reserveScheduleRepository;

	private final ReserveApplyRepository reserveApplyRepository;

	// 모든 회원을 조회해서 리턴하는 메서드
	public List<Member> findMembers() {

		return memberRepository.findAll();
	}

	// 고유코드로 회원을 조회하는 메서드
	public Member findOne(Long memCd) {
		return memberRepository.findByCd(memCd);
	}

	// 회원정보를 수정하는 메서드.. 회원 고유번호 리턴함
	public Long updateMember(ModMemberInfoDto modMemberInfoDto) throws Exception {
		Member member = memberRepository.findById(modMemberInfoDto.getId());
		member.updateMember(modMemberInfoDto);

		return member.getCd();
	}

	// 아이디로 회원을 조회하는 메서드
	public Member findMember(String id) {
		return memberRepository.findById(id);
	}

	// 회원 삭제
	public void deleteMember(Long memCd) {
		memberRepository.deleteById(memCd);
	}

	// 회원 이용제한
	public void restrictMember(String id) {
		Member member = memberRepository.findById(id);
		member.restrictMember();
	}

	// 일주일 이용제한 Mysql에 저장
	public void restrictMemberForOneWeek(Long memCd) {
		Member member = memberRepository.findById(memCd).orElseThrow(EntityNotFoundException::new);
		member.restrictMember();

		LocalDateTime stdt = LocalDateTime.now();
		LocalDateTime eddt = stdt.plusWeeks(1);

		MemberRestrict memberRestrict = new MemberRestrict();
		memberRestrict.setStdt(stdt);
		memberRestrict.setEddt(eddt);
		memberRestrict.setStatus(RestrictStatus.RESTRICTED);
		memberRestrict.setMember(member);

		if (member.getMemberRestricts() == null) {
			member.setMemberRestricts(new ArrayList<>());
		}

		member.getMemberRestricts().add(memberRestrict); // MemberRestrict 객체를 리스트에 추가

		member.setStatus(MemberStatus.RESTRICT);

		memberRepository.save(member);
	}

	// MemberRestrict 데이터 날리기 ->
	public void cancelMemberRestriction(Long memCd) {
		Member member = memberRepository.findById(memCd).orElseThrow(EntityNotFoundException::new);

		member.setStatus(MemberStatus.GENERAL);

		if (member.getMemberRestricts() != null && !member.getMemberRestricts().isEmpty()) {
			member.getMemberRestricts().clear(); // memberRestricts 리스트에서 모든 MemberRestrict 제거
		}

		memberRepository.save(member);

		// memberRestricts 테이블의 데이터 삭제
		memberRestrictRepository.deleteByMember(member);
	}

	@Transactional(readOnly = true)
	public Page<Member> getAdminMemberInfoPage(MemberSearchDto memberSearchDto, Pageable pageable) {
		return memberRepository.getAdminMemberInfoPage(memberSearchDto, pageable);
	}

	// 예약일정데이터를 db에 추가하는 메서드
	// 예약일정이 이미 등록되었을 경우는 중복으로 등록되지않음.
	private void setReserveSchedule(String date, String time) {
		ReserveSchedule foundRs = reserveScheduleRepository.findByReserveDateAndReserveTime(date, time);
		if (foundRs == null) {
			ReserveSchedule rs = ReserveSchedule.createReserveSchedule(date, time);

			reserveScheduleRepository.save(rs);
		}
	}

	// 선택된 날짜, 시간으로 예약일정 데이터 만드는 메서드
	public void saveReserveSchedule(List<String> schDateList, String am, String pm) {
		for (String schDate : schDateList) {
			if (!StringUtils.isEmpty(am)) {
				setReserveSchedule(schDate, am);
			}
			if (!StringUtils.isEmpty(pm)) {
				setReserveSchedule(schDate, pm);
			}
		}
	}

	// 예약일정을 삭제하는 메서드
	public void deleteReserveScedult(Long id) {
		reserveScheduleRepository.deleteById(id);
	}
	
	// 상담 일정을 전부 조회하는 메서드
	public List<ReserveApply> getReserveApplyList() {
		return reserveApplyRepository.findAll();
	}
}
