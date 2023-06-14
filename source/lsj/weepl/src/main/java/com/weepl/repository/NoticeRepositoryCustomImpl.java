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
import com.weepl.dto.NoticeSearchDto;
import com.weepl.entity.Notice;
import com.weepl.entity.QNotice;

public class NoticeRepositoryCustomImpl implements NoticeRepositoryCustom{ //인터페이스를 상속하여 구현

	private JPAQueryFactory queryFactory; //동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용한다.
	
	
	public NoticeRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); //JPAQueryFactory 생성자로 EntityManger 객체를 넣어준다
	}
	
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		//searchDateType의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 글만 조회한다.
		//searchDateType의 값이 1m인 경우 dateTime의 시간을 한달전으로 세팅 후 최근 한달동안 등록된 글만 조회하도록 조건값을 반환한다.
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType==null) {
			return null;
		}
		else if(StringUtils.equals("1d", searchDateType)) {
			dateTime=dateTime.minusDays(1);
		}
		else if(StringUtils.equals("1w", searchDateType)) {
			dateTime=dateTime.minusWeeks(1);
		}
		else if(StringUtils.equals("1m", searchDateType)) {
			dateTime=dateTime.minusMonths(1);
		}
		else if(StringUtils.equals("6m", searchDateType)) {
			dateTime=dateTime.minusMonths(6);
		}
		return QNotice.notice.regDt.after(dateTime);
	}
	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) { 
		//searchBy의 값에 따라서 공지사항 제목에 검색어를 포함하고 있는 글 
		
		
		if(StringUtils.equals("title", searchBy)) {
			return QNotice.notice.title.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	
	
	@Override
	public Page<Notice> getAdminNoticePage(NoticeSearchDto noticeSearchDto, Pageable pageable) {
		
		QueryResults<Notice> results = queryFactory //queryfactory를 이용해서 쿼리를 생성한다.
			.selectFrom(QNotice.notice) //데이터를 조회하기 위해서 QNotice의 notice를 지정한다.
			.where(regDtsAfter(noticeSearchDto.getSearchDateType()),  //BooleanExpression 반환하는 조건문들을 넣어준다
					searchByLike(noticeSearchDto.getSearchBy(), noticeSearchDto.getSearchQuery())) //컴마 단위로 넣어줄 경우 and조건으로 인식
			.orderBy(QNotice.notice.cd.desc())
			.offset(pageable.getOffset()) //데이터를 가지고 올 시작 인덱스를 지정한다.
			.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수를 지정한다.
			.fetchResults(); //조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환한다. 상품 데이터 리스트 조회 및 상품 데이터 전체 개수를 조회하는 2번의 쿼리문이 실행된다.
		
		List<Notice> content = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(content, pageable, total); //조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
	} 
	

}
