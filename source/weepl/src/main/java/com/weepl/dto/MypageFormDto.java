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
public class MypageFormDto {
	private String id;
	
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
	
	public MypageFormDto() {
	}

	public static MypageFormDto of(Member member) {
		 MypageFormDto mypageFormDto = new MypageFormDto();
		 mypageFormDto.setName(member.getName());
		 mypageFormDto.setId(member.getId());
		 mypageFormDto.setEmail(member.getEmail());
		 mypageFormDto.setGender(member.getGender());
		 mypageFormDto.setTel1(member.getTel1());
		 mypageFormDto.setTel2(member.getTel2());
		 mypageFormDto.setTel3(member.getTel3());
		 mypageFormDto.setBirY(member.getBirY());
		 mypageFormDto.setBirM(member.getBirM());
		 mypageFormDto.setBirD(member.getBirD());
		 mypageFormDto.setAddr(member.getAddr());
		 mypageFormDto.setAddrDtl(member.getAddrDtl());
		 mypageFormDto.setAddrPost(member.getAddrPost());
	      
	      return mypageFormDto;
	}
	
}
