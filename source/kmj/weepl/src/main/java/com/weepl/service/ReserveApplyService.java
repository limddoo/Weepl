package com.weepl.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.ReserveApplyDto;
import com.weepl.entity.ReserveApply;
import com.weepl.repository.ReserveApplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReserveApplyService {

	private final ReserveApplyRepository reserveApplyRepository;
	
	public Long saveReserveApply(ReserveApplyDto reserveApplyDto) {
		ReserveApply reserveApply = reserveApplyDto.reserveApplyDtotoReserveApply();
		
		reserveApplyRepository.save(reserveApply);
		
		return reserveApply.getCd();
	}
	
	 public List<Map<String, Object>> addReserveApply(){
		 Map<String, Object> reserveApply = new HashMap<String, Object>();
	        List<Map<String, Object>> reserveApplyList = new ArrayList<Map<String, Object>>();
	        reserveApply.put("title", "test");
	        reserveApply.put("start", LocalDate.now());
	        reserveApply.put("end",LocalDate.now());
	        reserveApplyList.add(reserveApply);
	        
	        reserveApply = new HashMap<String, Object>();
	        reserveApply.put("title", "test2");
	        reserveApply.put("start", LocalDate.now().plusDays(4));
	        reserveApply.put("end",LocalDate.now().plusDays(5));
	        reserveApplyList.add(reserveApply);
	        
	        return reserveApplyList;
	 }
}
