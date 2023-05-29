package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.constant.Role;
import com.weepl.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
	Member findById(String id);
	Member findByCd(Long cd);
	Member findByRole(Role role);
}
