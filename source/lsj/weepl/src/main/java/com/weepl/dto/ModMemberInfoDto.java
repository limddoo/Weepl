package com.weepl.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.weepl.entity.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModMemberInfoDto {
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
	
	public ModMemberInfoDto() {
	}

	public static ModMemberInfoDto of(Member member) {
		ModMemberInfoDto modMemberInfoDto= new ModMemberInfoDto();
		modMemberInfoDto.setName(member.getName());
		modMemberInfoDto.setId(member.getId());
		modMemberInfoDto.setEmail(member.getEmail());
		modMemberInfoDto.setGender(member.getGender());
		modMemberInfoDto.setTel1(member.getTel1());
		modMemberInfoDto.setTel2(member.getTel2());
		modMemberInfoDto.setTel3(member.getTel3());
		modMemberInfoDto.setBirY(member.getBirY());
		modMemberInfoDto.setBirM(member.getBirM());
		modMemberInfoDto.setBirD(member.getBirD());
		modMemberInfoDto.setAddr(member.getAddr());
		modMemberInfoDto.setAddrDtl(member.getAddrDtl());
		modMemberInfoDto.setAddrPost(member.getAddrPost());
	      
	      return modMemberInfoDto;
	}
	
}

