package com.weepl.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weepl.dto.ChatDto;
import com.weepl.dto.ChatRoom;
import com.weepl.service.ConsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ConsService consService;

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("{}", payload);
		ChatDto chatMessage = objectMapper.readValue(payload, ChatDto.class);
		log.info("{}", chatMessage);
		consService.saveConsContent(chatMessage);
		ChatRoom chatRoom = consService.findRoomById(chatMessage.getRoomId());
		chatRoom.handlerActions(session, chatMessage, consService);
	}
}