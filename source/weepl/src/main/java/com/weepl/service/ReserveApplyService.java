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

	public List<Map<String, Object>> getReservations() {

		List<Map<String, Object>> reserveApplyList = new ArrayList<Map<String, Object>>();

		List reserveScheduleList = reserveScheduleRepository.findAll();

		for (int i = 0; i < reserveScheduleList.size(); i++) {
			Map<String, Object> reserveApply = new HashMap<String, Object>();
			StringBuilder sb = new StringBuilder(); // JAVA Default date-time형식인 ISO8601로 표기를 바꾸기 위함, StringBuilder는 새로운
													// 겍체 생성 없이 기존 문자열에 계속 더해주므로 메모리 효율이 높음
			ObjectMapper objectMapper = new ObjectMapper();
			Map result = objectMapper.convertValue(reserveScheduleList.get(i), Map.class); // List를 Map으로 형변환

			// ISO8601 형식으로 캘린더에 렌더링해야 정상작동
			sb.append(result.get("reserveDate"));
			sb.append("T");
			sb.append(result.get("reserveTime"));

			//id, title, start, color 모두 calendar의 event 객체의 속성들임
			reserveApply.put("id", result.get("cd"));
			reserveApply.put("title", result.get("status"));
			reserveApply.put("start", sb);
			if (result.get("status").equals("예약완료")) {
				reserveApply.put("color", "red"); // 예약완료시 캘린더에 표시되는 색상을 빨간색으로 변경
			}
			sb = null; // 다음 for문이 돌기 전에 초기화
			result.clear(); // 다음 for문이 돌기 전에 초기화
			reserveApplyList.add(reserveApply);
		}
		return reserveApplyList;
	}
	
	
	@Transactional(readOnly = true)
	public ReserveApplyDto getReserveDtl(Long reserveScheduleCd) {
		ReserveApply reserveApply = reserveApplyRepository.findByReserveScheduleCd(reserveScheduleCd);
		ReserveApplyDto reserveApplyDto = new ReserveApplyDto();
		if (reserveApply != null) {
			reserveApplyDto = ReserveApplyDto.reserveApplyToReserveApplyDto(reserveApply);
			reserveApplyDto.setMemCd(reserveApply.getMember().getCd());
			reserveApplyDto.setId(reserveApply.getMember().getId()); // 컨트롤러에서 현재 로그인한 사용자 ID와 비교하기 위함
		}
		return reserveApplyDto;
	}
	
	public Long saveReserveApply(ReserveApplyDto reserveApplyDto) {
		//reserveApply entity 객체 생성 후 dto의 값들을 넣어주고, 현재 로그인한 사용자의 mem_cd값을 더해준다
		ReserveApply reserveApply = reserveApplyDto.reserveApplyDtoToReserveApply();
		reserveApply.setMember(memberRepository.findById(reserveApplyDto.getName()));
		reserveApplyRepository.save(reserveApply);
		
		//reserveSchedule 테이블의 status를 예약가능 -> 예약완료로 변경
		ReserveSchedule reserveSchedule = reserveScheduleRepository.findById(reserveApplyDto.getReserveScheduleCd())
				.orElseThrow(EntityNotFoundException::new);
		reserveSchedule.updateReserveSchedule(reserveApplyDto.getReserveScheduleCd(), "예약완료");

		return reserveApply.getReserveApplyCd();
	}

}
