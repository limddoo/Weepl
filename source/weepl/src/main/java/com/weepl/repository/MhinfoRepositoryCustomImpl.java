package com.weepl.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weepl.constant.MhinfoCate;
import com.weepl.dto.SearchDto;
import com.weepl.entity.Mhinfo;
import com.weepl.entity.QMhinfo;

public class MhinfoRepositoryCustomImpl implements MhinfoRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public MhinfoRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	
	
	private BooleanExpression searchByCate(String searchBy, String searchQuery) {

		if (searchBy == null || StringUtils.equals("all", searchBy)) {
			return Expressions.asBoolean(true).isTrue();
		} else if ("SCHOOL".equals(searchBy)) {
			return QMhinfo.mhinfo.mhinfoCate.eq(MhinfoCate.SCHOOL);
		} else if ("MIND".equals(searchBy)) {
			return QMhinfo.mhinfo.mhinfoCate.eq(MhinfoCate.MIND);
		} else if ("RELATIONSHIP".equals(searchBy)) {
			return QMhinfo.mhinfo.mhinfoCate.eq(MhinfoCate.RELATIONSHIP);
		}
		return Expressions.asBoolean(true).isTrue();
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("title", searchBy)) {
			return QMhinfo.mhinfo.title.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createdBy", searchBy)) {
			return QMhinfo.mhinfo.content.like("%" + searchQuery + "%");
		}
		return null;
	}

	@Override
	public Page<Mhinfo> getMhinfoPage(SearchDto mhinfoSearchDto, Pageable pageable) {
	    BooleanExpression categoryExpression = null;
	    if (mhinfoSearchDto.getSearchByCate() != null) {
	        categoryExpression = searchByCate(mhinfoSearchDto.getSearchByCate(), mhinfoSearchDto.getSearchQuery());
	    }

	    BooleanExpression likeExpression = StringUtils.isEmpty(mhinfoSearchDto.getSearchQuery()) ? null :
	            searchByLike(mhinfoSearchDto.getSearchByLike(), mhinfoSearchDto.getSearchQuery());

	    QueryResults<Mhinfo> results;
	    if (likeExpression != null) {
	        if (categoryExpression != null) {
	            results = queryFactory.selectFrom(QMhinfo.mhinfo)
	                    .where(categoryExpression, likeExpression)
	                    .orderBy(QMhinfo.mhinfo.cd.desc())
	                    .offset(pageable.getOffset())
	                    .limit(pageable.getPageSize())
	                    .fetchResults();
	        } else {
	            results = queryFactory.selectFrom(QMhinfo.mhinfo)
	                    .where(likeExpression)
	                    .orderBy(QMhinfo.mhinfo.cd.desc())
	                    .offset(pageable.getOffset())
	                    .limit(pageable.getPageSize())
	                    .fetchResults();
	        }
	    } else {
	        if (categoryExpression != null) {
	            results = queryFactory.selectFrom(QMhinfo.mhinfo)
	                    .where(categoryExpression)
	                    .orderBy(QMhinfo.mhinfo.cd.desc())
	                    .offset(pageable.getOffset())
	                    .limit(pageable.getPageSize())
	                    .fetchResults();
	        } else {
	            results = queryFactory.selectFrom(QMhinfo.mhinfo)
	                    .orderBy(QMhinfo.mhinfo.cd.desc())
	                    .offset(pageable.getOffset())
	                    .limit(pageable.getPageSize())
	                    .fetchResults();
	        }
	    }

	    List<Mhinfo> title = results.getResults();
	    long total = results.getTotal();

	    return new PageImpl<>(title, pageable, total);
	}

}
