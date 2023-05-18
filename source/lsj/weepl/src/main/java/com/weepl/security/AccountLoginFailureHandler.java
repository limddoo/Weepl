package com.weepl.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.constant.MemberStatus;
import com.weepl.entity.Member;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountLoginFailureHandler implements AuthenticationFailureHandler {
	private final MemberRepository memberRepository;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof BadCredentialsException) { // 그냥 아이디, 비밀번호가 일치하지 않아서 진입했을경우
        	String id = request.getParameter("id");
            // 로그인 실패 카운트 수정하기 위해 엔티티 조회
            Member member = memberRepository.findById(id);
            if (member == null) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }

            if(MemberStatus.QUIT.equals(member.getStatus())){
            	request.setAttribute("isLocked", true);
            } else {
                request.setAttribute("isFailed", true);
            }
        }else if(exception instanceof LockedException){ // LoginSuccessHandler에서 LockedException발생시 넘어 온 경우
            request.setAttribute("isLocked", true);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        requestDispatcher.forward(request, response);
    }
}