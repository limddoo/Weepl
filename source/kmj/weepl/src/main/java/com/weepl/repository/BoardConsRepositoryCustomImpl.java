package com.weepl.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Pageable;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weepl.entity.BoardCons;
import com.weepl.entity.QBoardCons;

public class BoardConsRepositoryCustomImpl implements BoardConsRepositoryCustom{ //인터페이스를 상속하여 구현
	private JPAQueryFactory queryFactory; //동적으로 쿼리를 생성하기 위해서 JPAQueryFactory 클래스를 사용한다.
	
	
	public BoardConsRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); //JPAQueryFactory 생성자로 EntityManger 객체를 넣어준다
	}
	
	@Override
	public Map<String,Object> getBoardConsList(Pageable pageable) {
		Map<String,Object> result = new HashMap<>();
		QueryResults<BoardCons> results = queryFactory //queryfactory를 이용해서 쿼리를 생성한다.
			.selectFrom(QBoardCons.boardCons) //데이터를 조회하기 위해서 QNotice의 notice를 지정한다.
			.orderBy(QBoardCons.boardCons.cd.desc())
			.offset(pageable.getOffset()) //데이터를 가지고 올 시작 인덱스를 지정한다.
			.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수를 지정한다.
			.fetchResults(); //조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환한다. 상품 데이터 리스트 조회 및 상품 데이터 전체 개수를 조회하는 2번의 쿼리문이 실행된다.
		
		List<BoardCons> content = results.getResults();

		long total = results.getTotal();
		
		result.put("content", content);
		result.put("total", total);
		 //조회한 데이터를 Page 클래스의 구현체인 PageImpl객체로 반환한다.
		return  result;
	} 
	
	
}
