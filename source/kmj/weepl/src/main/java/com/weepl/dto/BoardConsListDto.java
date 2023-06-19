package com.weepl.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsNmem;
import com.weepl.entity.Member;
import com.weepl.repository.BoardConsNmemRepository;
import com.weepl.repository.BoradConsRepository;
import com.weepl.repository.MemberRepository;
import com.weepl.service.BoardConsService;
import com.weepl.service.MemberService;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter @Setter @ToString
public class BoardConsListDto {
	
	private Long cd;
	private String title;
	private String name;
	private LocalDateTime regDt;

	

}
