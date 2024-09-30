package com.dang.travel.security.sessionauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.member.dto.response.AuthRes;
import com.dang.travel.security.sessionauth.domain.CustomUserDetails;
import com.dang.travel.security.sessionauth.dto.LoginReq;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class SessionAuthController {

	private final AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginReq loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);

		// 인증이 성공하면 SecurityContextHolder에 인증 정보를 저장 (세션 자동 관리)
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return ResponseEntity.ok("로그인 성공");
	}

	@GetMapping("/check")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<AuthRes> checkAuthentication(@AuthenticationPrincipal CustomUserDetails userDetails) {

		if (userDetails == null) {
			// 사용자 정보가 없으면 접근 거부 예외를 던짐
			throw new AccessDeniedException("인증되지 않은 사용자입니다.");
		}
		// 인증된 사용자 정보 반환
		AuthRes authRes = AuthRes.builder()
			.email(userDetails.getUsername())
			.nickname(userDetails.getUser().getNickname())
			.authenticated(true)
			.build();

		return ResponseEntity.ok(authRes);  // 로그인된 사용자 정보 반환
	}
}
