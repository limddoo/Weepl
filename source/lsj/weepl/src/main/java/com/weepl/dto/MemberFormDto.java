package com.weepl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
}
