package com.weepl.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.MemberFormDto;
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
		Member findMember = memberRepository.findByEmail(member.getEmail());
		if(findMember != null) {
			throw new IllegalStateException("이미 가입된 이메일입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
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

	public boolean checkIdDuplicate(String id) {
		Member findMember = memberRepository.findById(id);
		if(findMember != null) // 이미 admin계정이 있을경우
			return true;
		return false;
	}
	
	public String findId(String name, String tel1, String tel2, String tel3) {
		Member findMember = memberRepository.findByNameAndTel1AndTel2AndTel3(name, tel1, tel2, tel3);
		if(findMember == null) {
			return null;
		}
		MemberFormDto memberDto = MemberFormDto.of(findMember);
		return memberDto.getId();
	}

	public String findPwd(String id, String name, String email) {
		Member findMember = memberRepository.findByIdAndNameAndEmail(id, name, email);
		if(findMember == null) {
			return null;
		}
		return findMember.getId();
	}
	
	public void updateMemberPwd(String id, String pwd, PasswordEncoder passwordEncoder) {
		Member findMember = memberRepository.findById(id);
		String password = passwordEncoder.encode(pwd);
		findMember.setPwd(password);
		memberRepository.save(findMember);
	}
}
