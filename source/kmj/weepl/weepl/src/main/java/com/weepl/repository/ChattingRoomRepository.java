package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.ChattingRoom;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
	ChattingRoom findByMemberAndReserveApply(Member member, ReserveApply reserveApply);
}
