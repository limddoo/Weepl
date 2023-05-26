package com.weepl.repository;

import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.weepl.entity.ReserveApply;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ReserveApplyRepositoryTest {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ReserveApplyRepository reserveApplyRepository;
	
	public void createReserveApply() {
		for (int i = 1; i <= 10; i++) {
			ReserveApply reserveApply = new ReserveApply();
			
			reserveApply.setConsDiv("consDiv");
			reserveApply.setConsReqContent("testContent"+i);
			reserveApply.setReserveStatus("진행중");
			
			ReserveApply savedReserveApply = reserveApplyRepository.save(reserveApply);
		}
	}
	
	@Test
	@DisplayName("비대면상담예약 테스트")
	public void addReserveApplyTest() {
		ReserveApply reserveApply = new ReserveApply();
		reserveApply.setConsDiv("consDiv");
		reserveApply.setConsReqContent("testContent");
		reserveApply.setReserveStatus("진행중");
		reserveApply.setReserveDt(LocalDateTime.now());
		reserveApply.setReserveStatus("진행중");
		ReserveApply savedReserveApply = reserveApplyRepository.save(reserveApply);
		System.out.println(savedReserveApply.toString());
	}
}
