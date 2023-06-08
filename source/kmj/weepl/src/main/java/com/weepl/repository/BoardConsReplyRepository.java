package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsReply;

public interface BoardConsReplyRepository extends JpaRepository<BoardConsReply, Long> {
	BoardConsReply findByBoardCons(BoardCons boardCons);
}
