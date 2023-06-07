package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weepl.dto.ReserveApplyDto;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;

@Repository
public interface ReserveApplyRepository extends JpaRepository<ReserveApply, Long> {
	ReserveApply findByMemberCd(Member memberCd);

	@Query("select r from ReserveApply r where r.reserveSchedule.cd=:reserveScheduleCd")
	ReserveApply findByReserveScheduleCd(Long reserveScheduleCd);
}
