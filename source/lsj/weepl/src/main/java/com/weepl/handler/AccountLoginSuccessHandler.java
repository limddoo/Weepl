package com.weepl.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.constant.MemberStatus;
import com.weepl.entity.Member;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional
public class AccountLoginSuccessHandler implements AuthenticationSuccessHandler {
	private RequestCache requestCache = new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private final MemberRepository memberRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		// 로그인 5회 실패 전 로그인 성공할경우 실패 횟수 리셋을 위해 엔티티 조회
		Member member = memberRepository.findById(user.getUsername());
		if (MemberStatus.QUIT.equals(member.getStatus())) {
			// 아래 예외로 인해 로그인 실패가 발생하고, 로그인 실패 핸들러 호출됨
			throw new LockedException("탈퇴처리한 회원입니다.");
		} else if (MemberStatus.RESTRICT.equals(member.getStatus())) {
            throw new DisabledException("이용이 제한된 회원입니다.");
        }

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		// 인증이 필요한 리소스에 접근하려다 로그인 화면으로 넘어간경우
		if (savedRequest != null) {
			redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
		} else { // 직접 로그인 페이지로 이동해서 들어온경우 메인페이지로 리다이렉트
			redirectStrategy.sendRedirect(request, response, "/");
		}
	}
}