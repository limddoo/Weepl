package com.weepl.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weepl.dto.SweetSearchDto;
import com.weepl.entity.QSweetBoard;
import com.weepl.entity.SweetBoard;

public class SweetRepositoryCustomImpl implements SweetRepositoryCustom {
	// 동적으로 쿼리를 생성하기 위한 클래스
	private JPAQueryFactory queryFactory;
	
	public SweetRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchBoardDivEq(String searchBoardDiv) {
		return searchBoardDiv == null? null : QSweetBoard.sweetBoard.board_div.like(searchBoardDiv);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		if (StringUtils.equals("all",  searchDateType) || searchDateType == null) {
			return null;
		} else if (StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);
		} else if(StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);
		}
		return QSweetBoard.sweetBoard.regDt.after(dateTime);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if (StringUtils.equals("title", searchBy)) {
			return QSweetBoard.sweetBoard.title.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("content", searchBy)) {
			return QSweetBoard.sweetBoard.content.like("%" + searchQuery + "%");
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QSweetBoard.sweetBoard.createdBy.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	@Override
	public Page<SweetBoard> getSweetBoardPage(SweetSearchDto sweetSearchDto, Pageable pageable) {
		QueryResults<SweetBoard> results = queryFactory
				.selectFrom(QSweetBoard.sweetBoard)
				.where(regDtsAfter(sweetSearchDto.getSearchDateType()),
						searchBoardDivEq(sweetSearchDto.getSearchBoardDiv()),
						searchByLike(sweetSearchDto.getSearchBy(),
								sweetSearchDto.getSearchQuery()))
				.orderBy(QSweetBoard.sweetBoard.cd.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		List<SweetBoard> content = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(content, pageable, total);
	}
}
