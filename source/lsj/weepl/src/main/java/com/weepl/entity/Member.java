package com.weepl.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.weepl.constant.MemberStatus;
import com.weepl.constant.Role;
import com.weepl.dto.MemberFormDto;
import com.weepl.dto.ModMemberInfoDto;
import com.weepl.dto.MypageFormDto;

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
		if("COUNSELOR".equals(memberFormDto.getRole())) {
			member.setRole(Role.COUNSELOR);
		} else {
			member.setRole(Role.CLIENT);
		}
		return member;
	}
	
	public void updateMember(MypageFormDto mypageFormDto){
		this.name = mypageFormDto.getName();
		this.email = mypageFormDto.getEmail();
		this.gender = mypageFormDto.getGender();
		this.tel1 = mypageFormDto.getTel1();
		this.tel2 = mypageFormDto.getTel2();
		this.tel3 = mypageFormDto.getTel3();
		this.birY = mypageFormDto.getBirY();
		this.birM = mypageFormDto.getBirM();
		this.birD = mypageFormDto.getBirD();
		this.addr = mypageFormDto.getAddr();
		this.addrDtl = mypageFormDto.getAddrDtl();
		this.addrPost = mypageFormDto.getAddrPost();
	}
	
	public void updateMember(ModMemberInfoDto modMemberInfoDto){
		this.name = modMemberInfoDto.getName();
		this.email = modMemberInfoDto.getEmail();
		this.gender = modMemberInfoDto.getGender();
		this.tel1 = modMemberInfoDto.getTel1();
		this.tel2 = modMemberInfoDto.getTel2();
		this.tel3 = modMemberInfoDto.getTel3();
		this.birY = modMemberInfoDto.getBirY();
		this.birM = modMemberInfoDto.getBirM();
		this.birD = modMemberInfoDto.getBirD();
		this.addr = modMemberInfoDto.getAddr();
		this.addrDtl = modMemberInfoDto.getAddrDtl();
		this.addrPost = modMemberInfoDto.getAddrPost();
	}

	public void updateMemberPwd(String pwd, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(pwd);
		this.pwd = password;
	}
	
	public void quitMember() {
		this.status = MemberStatus.QUIT;
		this.qdate = LocalDateTime.now();
	}
	
	public void restrictMember() {
		this.status = MemberStatus.RESTRICT;
	}
	
	public void updateMemberNickName(String nickName) {
		this.nickName = nickName;
	}

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<MemberRestrict> memberRestricts;
	
	public void updateMemberStatus(MemberStatus newStatus) {
	    if (this.status == MemberStatus.RESTRICT && newStatus == MemberStatus.GENERAL) {
	        this.memberRestricts.clear();
	    }
	    this.status = newStatus;
	}
}
