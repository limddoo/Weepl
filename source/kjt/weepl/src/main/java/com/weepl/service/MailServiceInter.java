package com.weepl.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public interface MailServiceInter {
    
    // 메일 내용 작성 
    MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;

    // 랜덤 인증코드 생성
    String createKey();
    
    // 메일 발송
    String sendSimpleMessage(String to) throws Exception;

    // 비밀번호 찾기용 메일 작성
	MimeMessage createMessageForPwd(String to) throws MessagingException, UnsupportedEncodingException;

	// 비밀번호 찾기용 메일 발송
	String sendSimpleMessageforPwd(String to) throws Exception;

}