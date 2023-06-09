package com.weepl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ChatRoom;
import com.weepl.entity.ChattingRoom;
import com.weepl.entity.Member;
import com.weepl.repository.ChattingRoomRepository;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoom> chatRooms;
    private final ChattingRoomRepository chattingRoomRepository;
    private final MemberRepository memberRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(randomId)
                .name(name)
                .build();
        chatRooms.put(randomId, chatRoom);
        saveChattingRoom(randomId);
        return chatRoom;
    }
    
    public String findChatRoom(String userId) {
    	Member member = memberRepository.findById(userId);
    	ChattingRoom chattingRoom = chattingRoomRepository.findByMember(member);
    	return chattingRoom.getRoomId();
    }
    
    private void saveChattingRoom(String roomId) {
    	Member member = memberRepository.findById("hong");
    	ChattingRoom cr = ChattingRoom.createChattingRoom(member, roomId);
    	chattingRoomRepository.save(cr);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}