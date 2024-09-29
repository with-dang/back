package com.dang.travel.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dang.travel.security.sessionauth.handler.CustomLogoutSuccessHandler;
import com.dang.travel.security.sessionauth.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)  // 폼 로그인을 비활성화
			.httpBasic(AbstractHttpConfigurer::disable)  // 기본 HTTP 인증 비활성화
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/login","/api/auth/check", "/api/member/signup").permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint((request, response, authException) -> {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");  // 401 Unauthorized 반환
				})
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없습니다.");  // 403 Forbidden 반환
				})
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // 필요한 경우에만 세션 생성
			)
			.logout(logout -> logout
				.logoutUrl("/api/auth/logout")  // 로그아웃 URL 설정
				.invalidateHttpSession(true)  // 세션 무효화
				.clearAuthentication(true)  // 인증 정보 제거
				.deleteCookies("JSESSIONID")  // JSESSIONID 쿠키 삭제
				.logoutSuccessHandler(customLogoutSuccessHandler)  // 커스텀 로그아웃 성공 핸들러
			)
			.userDetailsService(customUserDetailsService);

		return http.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}
