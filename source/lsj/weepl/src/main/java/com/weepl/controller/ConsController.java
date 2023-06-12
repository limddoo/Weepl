package com.weepl.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.ChatRoom;
import com.weepl.service.ConsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/cons")
public class ConsController {
    private final ConsService consService;
    
    // 상담 안내 페이지
    @GetMapping(value="/consInfo")
    public String consInfo() {
    	return "cons/consInfo";
    }
    
    // 비대면 상담을 위한 채팅방 생성(Ajax)
    @GetMapping(value="/createRoom")
    @ResponseBody
    public void createRoom(String name, Long reserveApplyCd) {
    	consService.createRoom(name, reserveApplyCd);
    }
    
    // 비대면 상담 참여를 위한 채팅방 검색(Ajax)
    @GetMapping(value="/findRoom")
    @ResponseBody
    public HashMap<String, Long> findRoom(String name, Long reserveApplyCd) {
    	HashMap<String, Long> map = new HashMap<>();
    	
    	map.put("roomId", consService.findChatRoom(name, reserveApplyCd));
        return map;
    }
    
    // 상담종료후 상태 갱신
    @GetMapping(value="/endCons")
    @ResponseBody
    public void endCons(Long reserveApplyCd) {
    	System.out.println("상담을 종료합니다.");
    	consService.endCons(reserveApplyCd);
    }

}