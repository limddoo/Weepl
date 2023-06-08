package com.weepl.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weepl.dto.ChatRoom;
import com.weepl.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    
    @GetMapping(value="/createRoom")
    @ResponseBody
    public HashMap<String, String> createRoom(String name) {
    	HashMap<String, String> map = new HashMap<>();
    	ChatRoom room = chatService.createRoom(name);
    	map.put("roomId", room.getRoomId());
        return map;
    }
    
    @GetMapping(value="/findRoom")
    @ResponseBody
    public HashMap<String, String> findRoom(String name) {
    	HashMap<String, String> map = new HashMap<>();
    	
    	map.put("roomId", chatService.findChatRoom(name));
        return map;
    }

    @GetMapping
    @ResponseBody
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}