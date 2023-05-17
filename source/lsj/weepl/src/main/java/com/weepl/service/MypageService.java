package com.weepl.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.MypageFormDto;
import com.weepl.entity.Member;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
	private final MemberRepository memberRepository;
	
	public Member findMember(String id) {
		return memberRepository.findById(id);
	}

	public void updateMember(MypageFormDto mypageFormDto) {
		Member member = memberRepository.findById(mypageFormDto.getId());
		validateDuplicateMember(member);
		member.updateMember(mypageFormDto);
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
}
