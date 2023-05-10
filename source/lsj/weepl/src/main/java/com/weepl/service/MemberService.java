package com.weepl.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.entity.Member;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		return memberRepository.save(member);
	}

	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
	}
	
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		Member member = memberRepository.findById(id);

		if (member == null) {
			throw new UsernameNotFoundException(id);
		}
		return User.builder()
				.username(member.getId())
				.password(member.getPwd())
				.roles(member.getRole().toString())
				.build();
	}
}
