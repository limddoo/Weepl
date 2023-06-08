package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.weepl.entity.ReserveApply;

@Repository
public interface ReserveApplyRepository extends JpaRepository<ReserveApply, Long> {
	
    @Query("select r from ReserveApply r where r.member.cd=:memberCd")
	List findByMember(Long memberCd);
    
    @Query("select r from ReserveApply r where r.reserveSchedule.cd=:reserveScheduleCd")
	ReserveApply findByReserveScheduleCd(Long reserveScheduleCd);
    
    @Modifying
    @Query("delete from ReserveApply r where r.reserveSchedule.cd=:id")
    void deleteReserveApply(@Param("id") Long id);
}
