package com.weepl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.ModMemberInfoDto;
import com.weepl.dto.MyConsDtlDto;
import com.weepl.dto.MyConsDto;
import com.weepl.dto.MypageFormDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.Member;
import com.weepl.repository.BoardConsRepository;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
	private final MemberRepository memberRepository;
	private final BoardConsRepository boardConsRepository;
	private BoardCons myCons;
	
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
	
	@Transactional(readOnly = true)
	public List<MyConsDto> MyList() {

		List<BoardCons> myCons = boardConsRepository.findAll();
		List<MyConsDto> myConsList = new ArrayList<>();
		for (BoardCons cons : myCons) {
			MyConsDto myConsDto = new MyConsDto();
			myConsDto.setCd(cons.getCd());
			myConsDto.setTitle(cons.getTitle());
			myConsDto.setReg_dt(cons.getRegDt());
			myConsList.add(myConsDto);
		}
		return myConsList;
	}
	
	@Transactional(readOnly = true)
	public Page<MyConsDto> getMyConsPage(Pageable pageable) {
		Map<String, Object> result = boardConsRepository.getBoardConsList(pageable);
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
	    consDtl.setMyConsCd(myConsCd);
	    System.out.println(myConsCd);
	    consDtl.setTitle("상담 제목");
	    consDtl.setContent("상담 내용");
	    // 상세 정보에 대한 필드 값을 설정합니다.

	    return consDtl;
	}
}
