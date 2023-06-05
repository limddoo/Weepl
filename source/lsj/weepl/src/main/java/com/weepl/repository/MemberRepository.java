package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	Member findByEmail(String email);
	Member findById(String id);
	Member findByNameAndTel1AndTel2AndTel3(String name, String tel1, String tel2, String tel3);
	Member findByIdAndNameAndEmail(String id, String name, String email);
	Member findByCd(Long cd);
}
