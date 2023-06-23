package com.weepl.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.weepl.service.ChatService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
	 private String roomId;
	    private String name;
	    private Set<WebSocketSession> sessions = new HashSet<>();

	    @Builder
	    public ChatRoom(String roomId, String name) {
	        this.roomId = roomId;
	        this.name = name;
	    }

	    public void handlerActions(WebSocketSession session, ChatDto chatMessage, ChatService chatService) {
	        if (chatMessage.getType().equals(ChatDto.MessageType.ENTER)) {
	            sessions.add(session);
	            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
	        }
	        sendMessage(chatMessage, chatService);

	    }

	    private <T> void sendMessage(T message, ChatService chatService) {
	        sessions.parallelStream()
	                .forEach(session -> chatService.sendMessage(session, message));
	    }
}
