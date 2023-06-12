package com.weepl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.weepl.entity.CompCons;
import com.weepl.entity.ReserveApply;

public interface CompConsRepository extends JpaRepository<CompCons, Long> {
	List<CompCons> findByReserveApply(ReserveApply reserveApply);
}
