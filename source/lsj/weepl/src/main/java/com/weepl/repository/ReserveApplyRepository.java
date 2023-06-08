package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;

@Repository
public interface ReserveApplyRepository extends JpaRepository<ReserveApply, Long>, ReserveApplyRepositoryCustom {
    ReserveApply findByMember(Member member);
    
    List<ReserveApply> findByReserveStatus(String status);
    
    @Query("select r from ReserveApply r where r.reserveSchedule.cd=:reserveScheduleCd")
	ReserveApply findByReserveScheduleCd(Long reserveScheduleCd);
}
