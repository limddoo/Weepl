package com.weepl.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.weepl.entity.Mhinfo;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class MhinfoRepositoryTest {

	@Autowired
	MhinfoRepository mhinfoRepository;
	
//	@Test
//	@DisplayName("정보글 저장 테스트")
//	public void createTest() {
//		MHInfo mHInfo = new MHInfo();
//		mHInfo.setCd(110);
//		mHInfo.setTitle("진로가고민되요");
//		mHInfo.setContent("가수가 된 이유");
//		mHInfo.setModDt(LocalDateTime.now());
//		mHInfo.setModDt(LocalDateTime.now());
//		
//		mHInfoRepository.save(mHInfo);
//	}
	@Test
	@DisplayName("정보글 저장 테스트")
	public void createTest() {
		Mhinfo mhinfo = new Mhinfo();
		mhinfo.setCd(110L);
		mhinfo.setTitle("진로가고민되요");
		mhinfo.setContent("xcvxcv이유");
		mhinfo.setModDt(LocalDateTime.now());
		mhinfo.setModDt(LocalDateTime.now());
		
		mhinfoRepository.save(mhinfo);
	}
}
