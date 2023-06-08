package com.weepl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.MhTestResultDto;
import com.weepl.entity.Member;
import com.weepl.entity.MhTestResult;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.MhTestResultRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MhTestResultService {
	
	private final MhTestResultRepository mhTestResultRepository;
	private final MemberRepository memberRepository;
	
	public void saveResult(MhTestResultDto mhTestResultDto) throws Exception{
		MhTestResult mhTestResult = mhTestResultDto.createResult(mhTestResultDto, null);
		mhTestResultRepository.save(mhTestResult);
		}
	public Member findMember(String id) {
		return memberRepository.findById(id);
	}

}
