package com.weepl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ChatDto;
import com.weepl.dto.ChatRoom;
import com.weepl.entity.ChattingRoom;
import com.weepl.entity.CompCons;
import com.weepl.entity.Member;
import com.weepl.entity.ReserveApply;
import com.weepl.repository.ChattingRoomRepository;
import com.weepl.repository.CompConsRepository;
import com.weepl.repository.MemberRepository;
import com.weepl.repository.ReserveApplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsService {
    private final ObjectMapper objectMapper;
    private Map<Long, ChatRoom> chatRooms;
    private final ChattingRoomRepository chattingRoomRepository;
    private final MemberRepository memberRepository;
    private final ReserveApplyRepository reserveApplyRepository;
    private final CompConsRepository compConsRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public ChatRoom findRoomById(Long roomId) {
        return chatRooms.get(roomId);
    }

    public ChatRoom createRoom(String name, Long reserveApplyCd) {
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId(reserveApplyCd)
                .name(name)
                .build();
        chatRooms.put(reserveApplyCd, chatRoom);
        saveChattingRoom(reserveApplyCd);
        return chatRoom;
    }
    
    public Long findChatRoom(String userId, Long reserveApplyCd) {
    	Member member = memberRepository.findById(userId);
    	ReserveApply reserveApply = reserveApplyRepository.getById(reserveApplyCd);
    	ChattingRoom chattingRoom = chattingRoomRepository.findByMemberAndReserveApply(member, reserveApply);
    	return chattingRoom.getReserveApply().getReserveApplyCd();
    }
    
    private void saveChattingRoom(Long reserveApplyCd) {
    	ReserveApply reserveApply = reserveApplyRepository.getById(reserveApplyCd);
    	ChattingRoom cr = ChattingRoom.createChattingRoom(reserveApply);
    	chattingRoomRepository.save(cr);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public void saveConsContent(ChatDto chatDto) {
    	CompCons compCons = chatDto.createCompCons();
    	compCons.setReserveApply(reserveApplyRepository.getById(chatDto.getRoomId()));
    	compConsRepository.save(compCons);
    }
}