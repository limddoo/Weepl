package com.weepl.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weepl.dto.ChatRoom;
import com.weepl.service.ChatService;

import lombok.RequiredArgsConstructor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    
    @PostMapping("/create")
    public ChatRoom createRoom(@RequestBody String name) {
        return chatService.createRoom(name);
    }

    @GetMapping("/find")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}