package com.weepl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.weepl.validate.TelValidationGroup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberFormDto {
	@NotBlank(message="아이디는 필수 입력 값입니다.")
	private String id;
	
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
	
	@NotEmpty(groups= TelValidationGroup.class)
	private String tel1;
	
	@NotEmpty(groups= TelValidationGroup.class)
	private String tel2;
	
	@NotEmpty(message="전화번호는 필수 입력 값입니다.")
	private String tel3;
	
	@NotBlank(message="생년월일은 필수 입력 값입니다.")
	private String birY;
	
	@NotBlank(message="생년월일은 필수 입력 값입니다.")
	private String birM;
	
	@NotBlank(message="생년월일은 필수 입력 값입니다.")
	private String birD;
	
	@NotBlank(message="기본 주소는 필수 입력 값입니다.")
	private String addr;
	
	@NotBlank(message="상세 주소는 필수 입력 값입니다.")
	private String addrDtl;
	
	@NotBlank(message="우편번호는 필수 입력 값입니다.")
	private String addrPost;
}
