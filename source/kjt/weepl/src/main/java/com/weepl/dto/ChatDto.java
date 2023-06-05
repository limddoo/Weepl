package com.weepl.dto;

import com.weepl.entity.CompCons;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatDto {
	// 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK, QUIT
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    
    
    public CompCons createCompCons() {
    	CompCons compCons = new CompCons();
    	compCons.setStatus(this.type.toString());
    	compCons.setName(this.getSender());
    	compCons.setMsg(this.message);
    	
    	return compCons;
    }
}
