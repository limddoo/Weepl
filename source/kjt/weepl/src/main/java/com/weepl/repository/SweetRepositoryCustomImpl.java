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
import com.weepl.dto.SearchDto;
import com.weepl.entity.QSweetBoard;
import com.weepl.entity.SweetBoard;

public class SweetRepositoryCustomImpl implements SweetRepositoryCustom {
	// 동적으로 쿼리를 생성하기 위한 클래스
	private JPAQueryFactory queryFactory;
	
	public SweetRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchBoardDivEq(String searchBoardDiv) {
		if("SCHOOL_TASK".equals(searchBoardDiv)) {
			return QSweetBoard.sweetBoard.board_div.eq("학교업무 공유게시판");
		} else if("CONSULTING".equals(searchBoardDiv)) {
			return QSweetBoard.sweetBoard.board_div.eq("상담전문성 공유게시판");
		} else if("FORM".equals(searchBoardDiv)) {
			return QSweetBoard.sweetBoard.board_div.eq("서식 공유게시판");
		} else if("FREETALK".equals(searchBoardDiv)) {
			return QSweetBoard.sweetBoard.board_div.eq("자유게시판");
		}
		return null;
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
	public Page<SweetBoard> getSweetBoardPage(SearchDto sweetSearchDto, Pageable pageable) {
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
