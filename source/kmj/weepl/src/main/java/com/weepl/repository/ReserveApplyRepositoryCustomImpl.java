package com.weepl.repository;

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

	@Override
	public Page<ReserveApply> getReserveApplyList(SearchDto searchDto, Pageable pageable) {
		BooleanExpression likeExpression = StringUtils.isEmpty(searchDto.getSearchQuery()) ? null
				: searchByLike(searchDto.getSearchBy(), searchDto.getSearchQuery());

		QueryResults<ReserveApply> results = queryFactory // queryfactory를 이용해서 쿼리를 생성한다.
				.selectFrom(QReserveApply.reserveApply)
				.where(likeExpression) // 이름으로 검색했을때 like 검색
				.where(QReserveApply.reserveApply.reserveStatus.eq("상담완료")) // 상태가 상담완료인 
				.orderBy(QReserveApply.reserveApply.reserveApplyCd.desc())
				.offset(pageable.getOffset()) // 데이터를 가지고 올 시작 인덱스를 지정한다.
				.limit(pageable.getPageSize()) // 한번에 가지고 올 최대 개수를 지정한다.
				.fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환한다.

		List<ReserveApply> content = results.getResults();

		long total = results.getTotal();

		// 조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
		return new PageImpl<>(content, pageable, total); // 조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
	}

}
