package com.weepl.repository;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.weepl.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, QuerydslPredicateExecutor<Item>, NoticeRepositoryCustom {
	List<Notice> findAll();
	List<Notice> findBytitle(String title);
	
}
