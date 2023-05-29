package com.weepl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.weepl.repository.BoardImgRepository;
import com.weepl.repository.MhinfoRepository;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MhinfoServiceTest {

	@Autowired
	MhinfoService mhinfoService;
	
	@Autowired
	MhinfoRepository mhinfoRepository;
	
	@Autowired
	BoardImgRepository boardImgRepository;

	List<MultipartFile> createMultipartFiles() throws Exception {
		List<MultipartFile> multipartFileList = new ArrayList<>();
			for(int i=0;i<5;i++){
				String path = "C:/shop/item/";
				String imageName = "image" + i + ".jpg";
				MockMultipartFile multipartFile =
				new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
				multipartFileList.add(multipartFile);
				}
				return multipartFileList;
		
	}
}
