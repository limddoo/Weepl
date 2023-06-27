package com.weepl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.weepl.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	private String role;

	private String _id;

	private String id;
	
	@NotEmpty(message="아이디 중복체크를 해주세요.")
	private String checked;
	
	@NotEmpty(message="비밀번호는 필수 입력 값입니다.")
	@Length(min=8, max=16, message="비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
	private String pwd;
	
	@NotEmpty(message="비밀번호확인은 필수입니다.")
	private String confirmPwd;
	
	@NotBlank(message="이름은 필수 입력 값입니다.")
	private String name;
	
	@NotEmpty(message="이메일은 필수 입력 값입니다.")
	@Email(message="이메일 형식으로 입력해주세요.")
	private String email;
	
	@NotBlank(message="성별을 선택해주세요.")
	private String gender;
	
	private String tel1;
	
	private String tel2;
	
	private String tel3;
	
	@Length(min=10, max=11, message="전화번호를 형식에 맞게 입력해주세요")
	private String tel;
	
	private String birY;
	
	private String birM;
	
	private String birD;
	
	@Length(min=8, max=8, message="생년월일을 형식에 맞게 입력해주세요")
	private String bir;
	
	@NotBlank(message="기본 주소는 필수 입력 값입니다.")
	private String addr;
	
	@NotBlank(message="상세 주소는 필수 입력 값입니다.")
	private String addrDtl;
	
	@NotBlank(message="우편번호는 필수 입력 값입니다.")
	private String addrPost;
	
	public MemberFormDto() {
	}
	
	public MemberFormDto(String role) {
		this.role = role;
	}
	
	static public MemberFormDto createAdmin() {
		 MemberFormDto memberFormDto = new MemberFormDto();
	      memberFormDto.setName("관리자");
	      memberFormDto.setId("admin");
	      memberFormDto.setPwd("12341234");
	      memberFormDto.setEmail("admin@abc.com");
	      memberFormDto.setGender("W");
	      memberFormDto.setTel1("010");
	      memberFormDto.setTel2("1234");
	      memberFormDto.setTel3("5678");
	      memberFormDto.setBirY("1995");
	      memberFormDto.setBirM("10");
	      memberFormDto.setBirD("26");
	      memberFormDto.setAddr("대전광역시 서구 둔산서로 17");
	      memberFormDto.setAddrDtl("양호빌딩 6층");
	      memberFormDto.setAddrPost("35235");
	      
	      return memberFormDto;
	}
	
	static public MemberFormDto createUser1() {
		 MemberFormDto memberFormDto = new MemberFormDto();
	      memberFormDto.setName("홍유저");
	      memberFormDto.setId("hong");
	      memberFormDto.setPwd("12341234");
	      memberFormDto.setEmail("hong@abc.com");
	      memberFormDto.setGender("M");
	      memberFormDto.setTel1("010");
	      memberFormDto.setTel2("1234");
	      memberFormDto.setTel3("1234");
	      memberFormDto.setBirY("1996");
	      memberFormDto.setBirM("05");
	      memberFormDto.setBirD("23");
	      memberFormDto.setAddr("대전광역시 서구 둔산서로 17");
	      memberFormDto.setAddrDtl("양호빌딩 6층");
	      memberFormDto.setAddrPost("35235");
	      
	      return memberFormDto;
	}
	
	static public MemberFormDto createUser2() {
		 MemberFormDto memberFormDto = new MemberFormDto();
	      memberFormDto.setName("김스윗");
	      memberFormDto.setId("kim");
	      memberFormDto.setPwd("12341234");
	      memberFormDto.setEmail("kim@abc.com");
	      memberFormDto.setGender("W");
	      memberFormDto.setTel1("010");
	      memberFormDto.setTel2("4321");
	      memberFormDto.setTel3("4321");
	      memberFormDto.setBirY("1995");
	      memberFormDto.setBirM("08");
	      memberFormDto.setBirD("30");
	      memberFormDto.setAddr("대전광역시 중구 목중로 26번길 10");
	      memberFormDto.setAddrDtl("어쩌구");
	      memberFormDto.setAddrPost("34814");
	      
	      return memberFormDto;
	}

	public static MemberFormDto of(Member member) {
		 MemberFormDto memberFormDto = new MemberFormDto();
	      memberFormDto.setName(member.getName());
	      memberFormDto.setId(member.getId());
	      memberFormDto.setPwd(member.getPwd());
	      memberFormDto.setEmail(member.getEmail());
	      memberFormDto.setGender(member.getGender());
	      memberFormDto.setTel1(member.getTel1());
	      memberFormDto.setTel2(member.getTel2());
	      memberFormDto.setTel3(member.getTel3());
	      memberFormDto.setBirY(member.getBirY());
	      memberFormDto.setBirM(member.getBirM());
	      memberFormDto.setBirD(member.getBirD());
	      memberFormDto.setAddr(member.getAddr());
	      memberFormDto.setAddrDtl(member.getAddrDtl());
	      memberFormDto.setAddrPost(member.getAddrPost());
	      
	      return memberFormDto;
	}
	
}
