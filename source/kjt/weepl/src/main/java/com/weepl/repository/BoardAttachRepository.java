package com.weepl.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.weepl.dto.NoticeAttachDto;
import com.weepl.entity.BoardAttach;

@Repository
public interface BoardAttachRepository extends JpaRepository<BoardAttach, Long> {

	List<BoardAttach> findByNotice_NoticeCdOrderByCdAsc(Long noticeCd);
	
	List<BoardAttach> findByCdOrderByCdAsc(Long cd);
	
	List<BoardAttach> findBySweetBoardCdOrderByCdAsc(Long cd);

}
