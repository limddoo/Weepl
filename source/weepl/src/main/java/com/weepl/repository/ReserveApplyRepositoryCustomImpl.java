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
import com.weepl.entity.QCompCons;
import com.weepl.entity.QMember;
import com.weepl.entity.QReserveApply;
import com.weepl.entity.ReserveApply;

public class ReserveApplyRepositoryCustomImpl implements ReserveApplyRepositoryCustom { // 인터페이스를 상속하여 구현
	private JPAQueryFactory queryFactory; // 동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용한다.

	public ReserveApplyRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory 생성자로 EntityManger 객체를 넣어준다
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("name", searchBy)) {
			return QReserveApply.reserveApply.member.name.like("%" + searchQuery + "%");
		}
		return null;
	}
	
	private BooleanExpression searchByEq(String status) {
		if (StringUtils.equals("reserved", status)) {
			return QReserveApply.reserveApply.reserveStatus.eq("예약완료");
		} else if(StringUtils.equals("compcons", status)) {
			return QReserveApply.reserveApply.reserveStatus.eq("상담완료");
		}
		return null;
	}
	
	private BooleanExpression jdateAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType==null) {
			return null;
		}else if(StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);
		} else if(StringUtils.equals("1w", searchDateType)){
			dateTime = dateTime.minusWeeks(1);
		} else if(StringUtils.equals("1m", searchDateType)){
		dateTime = dateTime.minusMonths(1);
		} else if(StringUtils.equals("6m", searchDateType)){
		dateTime = dateTime.minusMonths(6);
		}
		return QReserveApply.reserveApply.reserveDt.after(dateTime);
	}

	@Override
	public List<ReserveApply> getReserveApplyList(SearchDto searchDto, String status) {
		BooleanExpression likeExpression = StringUtils.isEmpty(searchDto.getSearchQuery()) ? null
				: searchByLike(searchDto.getSearchBy(), searchDto.getSearchQuery());
		
		BooleanExpression eqExpression = StringUtils.isEmpty(status) ? null
				: searchByEq(status);

		QueryResults<ReserveApply> results = queryFactory // queryfactory를 이용해서 쿼리를 생성한다.
				.selectFrom(QReserveApply.reserveApply)
				.where(jdateAfter(searchDto.getSearchDateType()), likeExpression, eqExpression) // 이름으로 검색했을때 like 검색
				.orderBy(QReserveApply.reserveApply.reserveApplyCd.desc())
				.fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환한다.

		// 조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
		return results.getResults(); // 조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
	}

}
