package com.weepl.entity;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.weepl.constant.MemberStatus;
import com.weepl.constant.Role;
import com.weepl.dto.MemberFormDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member{
	@Id
	@Column(name = "mem_cd")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cd;

	@Column(name = "mem_id", unique = true)
	private String id;

	private String pwd;
	
	private String name;

	@Column(unique = true)
	private String email;

	private String gender;
	
	private String tel1;
	
	private String tel2;
	
	private String tel3;
	
	@Column(name = "bir_y")
	private String birY;
	
	@Column(name = "bir_m")
	private String birM;
	
	@Column(name = "bir_d")
	private String birD;
	
	private String addr;
	
	private String addrDtl;
	
	private String addrPost;
	
	private LocalDateTime jdate;
	
	private LocalDateTime qdate;
	
	@Column(name = "mem_status")
	@Enumerated(EnumType.STRING)
	private MemberStatus status;
	
	@Column(unique = true)
	private String nickName;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
		Member member = new Member();
		member.setId(memberFormDto.getId());
		String password = passwordEncoder.encode(memberFormDto.getPwd());
		member.setPwd(password);
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setGender(memberFormDto.getGender());
		member.setTel1(memberFormDto.getTel1());
		member.setTel2(memberFormDto.getTel2());
		member.setTel3(memberFormDto.getTel3());
		member.setBirY(memberFormDto.getBirY());
		member.setBirM(memberFormDto.getBirM());
		member.setBirD(memberFormDto.getBirD());
		member.setAddr(memberFormDto.getAddr());
		member.setAddrDtl(memberFormDto.getAddrDtl());
		member.setAddrPost(memberFormDto.getAddrPost());
		member.setJdate(LocalDateTime.now());
		member.setStatus(MemberStatus.GENERAL);
		member.setRole(Role.ADMIN);
		return member;
	}
}
