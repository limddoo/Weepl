package com.weepl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weepl.entity.ReserveSchedule;

@Repository
public interface ReserveScheduleRepository extends JpaRepository<ReserveSchedule, Long>{
	public ReserveSchedule findByReserveDateAndReserveTime(String reserveDate, String reserveTime);
}
