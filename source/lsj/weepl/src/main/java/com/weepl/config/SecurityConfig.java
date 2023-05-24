package com.weepl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.weepl.handler.AccountLoginFailureHandler;
import com.weepl.handler.AccountLoginSuccessHandler;
import com.weepl.repository.MemberRepository;
import com.weepl.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberRepository memberRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().loginPage("/members/login") // 로그인 페이지 url을 설정
				.defaultSuccessUrl("/") // 로그인 성공 시 이동할 url
				.usernameParameter("id") // 로그인 시 사용할 파라미터 이름으로 id를 지정
				.passwordParameter("pwd")
				.successHandler(new AccountLoginSuccessHandler(memberRepository))
				.failureUrl("/members/login/error") // 로그인 실패 시 이동할 url을 설정
				.failureHandler(new AccountLoginFailureHandler())
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 url을 설정
				.logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 url을 설정
		;

		http.authorizeRequests().mvcMatchers("/", "/members/**","/ws/**","/chat/**","/favicon.ico").permitAll()
								.mvcMatchers("/admin/**").hasRole("ADMIN")
								.mvcMatchers("/mypage/**").hasAnyRole("CLIENT", "COUNSELOR")
								.anyRequest().authenticated()
								.and();

		http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/v2/api-docs","/swagger-resources/**",
                "/swagger-ui.html", "/swagger/**"); // static 디렉토리 하위 파일은 인증을 무시하도록 설정
	}
}
