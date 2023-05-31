package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.ChattingRoom;
import com.weepl.entity.Member;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
	ChattingRoom findByMember(Member member);
}
