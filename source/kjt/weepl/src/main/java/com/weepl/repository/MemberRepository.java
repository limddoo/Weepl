package com.weepl.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.weepl.entity.Member;
import com.weepl.entity.Mhinfo;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberRepositoryCustom{
	 										
	List<Member> findByIdOrName(String id, String name);
	
	Member findByEmail(String email);
	Member findById(String id);
	Member findByCd(Long cd);
	Member save(Member member);
	
	
	Optional<Member> findMemberByCd(Long cd);
}