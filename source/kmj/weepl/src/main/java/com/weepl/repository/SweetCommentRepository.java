package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.dto.SweetCommentDto;
import com.weepl.entity.SweetComment;

public interface SweetCommentRepository extends JpaRepository<SweetComment, Long> {
	List<SweetComment> findByCdOrderByCdAsc(Long cd);
}
