package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weepl.entity.Member;
import com.weepl.entity.MemberRestrict;

@Repository
public interface MemberRestrictRepository extends JpaRepository<MemberRestrict, Long> {
	void deleteByMember(Member member);
}
