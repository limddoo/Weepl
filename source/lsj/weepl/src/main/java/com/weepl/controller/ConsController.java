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
    
    @GetMapping(value="/consInfo")
    public String consInfo() {
    	return "cons/consInfo";
    }
    
    @GetMapping(value="/createRoom")
    @ResponseBody
    public void createRoom(String name, Long reserveApplyCd) {
    	consService.createRoom(name, reserveApplyCd);
    }
    
    @GetMapping(value="/findRoom")
    @ResponseBody
    public HashMap<String, Long> findRoom(String name, Long reserveApplyCd) {
    	HashMap<String, Long> map = new HashMap<>();
    	
    	map.put("roomId", consService.findChatRoom(name, reserveApplyCd));
        return map;
    }

}