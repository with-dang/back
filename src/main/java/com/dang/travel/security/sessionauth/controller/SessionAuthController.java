package com.dang.travel.security.sessionauth.controller;

import org.springframework.http.ResponseEntity;
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

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
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

	@PostMapping("/logout")
	public ResponseEntity<String> logout() {
		return ResponseEntity.ok("로그아웃 성공");
	}

	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
	@GetMapping("/check")
	public ResponseEntity<AuthRes> checkAuthentication(@AuthenticationPrincipal CustomUserDetails userDetails) {
		AuthRes authRes = AuthRes.builder()
			.email(userDetails.getUsername())
			.nickname(userDetails.getUser().getNickname())
			.authenticated(true)
			.build();
		return ResponseEntity.ok(authRes);  // 로그인된 사용자 정보 반환
	}
}
