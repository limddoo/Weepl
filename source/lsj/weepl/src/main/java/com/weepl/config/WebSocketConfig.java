package com.weepl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.weepl.handler.WebSocketHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
   
   @Autowired
   private final WebSocketHandler webSocketHandler;
   
   @Override
   public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
      registry.addHandler(webSocketHandler, "ws/chat").setAllowedOrigins("*");
//      .addInterceptors(new HttpSessionHandshakeInterceptor());   // interceptor for adding httpsession into websocket session
   }
   
}