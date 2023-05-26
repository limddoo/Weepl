package com.weepl.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.weepl.entity.Notice;
import com.weepl.entity.QNotice;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class NoticeRepositoryTest {

	@Autowired
	NoticeRepository noticeRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public void createNoticeList() {
		for (int i = 1; i <= 10; i++) {
			Notice notice = new Notice();
			notice.setTitle("testTitle"+i);
			notice.setContent("testContent"+i);		
			Notice savedNotice = noticeRepository.save(notice);
		}
	}
	
	@Disabled
	@Test
	@DisplayName("공지사항 테스트")
	public void addNoticeTest() {
		Notice notice = new Notice();
		
		notice.setTitle("testTitle");
		notice.setContent("testContent");
		
		Notice savedNotice = noticeRepository.save(notice);
		System.out.println(savedNotice.toString());
	}
	
	@Test
	@DisplayName("QueryDSL 공지사항 조회 테스트")
	public void noticeDslTest() {
		this.createNoticeList();
		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
		QNotice qNotice = QNotice.notice;
		
		List<Notice> noticeList = jpaQueryFactory.selectFrom(qNotice)
				.where(qNotice.title.contains("testTitle"))
				.orderBy(qNotice.noticeCd.asc())
				.fetch();
		
		for(Notice notice : noticeList) {
			System.out.println("--------------");
			System.out.println();
			System.out.println(notice.getNoticeCd());
			System.out.println(notice.getTitle());
			System.out.println(notice.getContent());
			System.out.println();
			System.out.println("--------------");
		}
	}
}
