package com.dang.travel.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.member.dto.request.SignupReq;
import com.dang.travel.member.dto.response.MemberInfoRes;
import com.dang.travel.member.service.MemberService;
import com.dang.travel.security.sessionauth.domain.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// 이메일 중복 확인 API
	@GetMapping("/check/email")
	public ResponseEntity<String> checkEmail(String email) {
		memberService.checkEmail(email);  // 중복 여부 확인

		return ResponseEntity.ok("사용 가능한 이메일입니다.");
	}

	// 회원 가입
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupReq signupRequest) {
		memberService.registerMember(signupRequest);
		return ResponseEntity.ok("회원가입이 완료되었습니다.");
	}

	// 회원 탈퇴
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
		String email = userDetails.getUsername();  // 현재 로그인된 사용자의 이메일
		memberService.deleteMember(email);
		return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
	}

	// 회원 정보 조회
	@PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능
	@GetMapping("/info")
	public ResponseEntity<MemberInfoRes> getMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails == null) {
			// 사용자 정보가 없으면 접근 거부 예외를 던짐
			throw new AccessDeniedException("인증되지 않은 사용자입니다.");
		}
		MemberInfoRes memberInfoRes = memberService.getMember(userDetails.getUsername());
		return ResponseEntity.ok(memberInfoRes);  // 로그인된 사용자 정보 반환

	}

}
