package com.dang.travel.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dang.travel.member.domain.Member;
import com.dang.travel.member.domain.Role;
import com.dang.travel.member.dto.request.SignupReq;
import com.dang.travel.member.dto.response.MemberInfoRes;
import com.dang.travel.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	// 회원 가입
	@Transactional
	public Member registerMember(SignupReq signupRequest) {
		checkEmail(signupRequest.getEmail());
		checkPhone(signupRequest.getPhone());

		// Member 빌더 패턴 사용
		Member member = Member.builder()
			.nickname(signupRequest.getNickname())
			.email(signupRequest.getEmail())
			.password(passwordEncoder.encode(signupRequest.getPassword()))
			.role(Role.USER)
			.phone(signupRequest.getPhone())
			.build();

		return memberRepository.save(member);
	}

	// 회원 정보 조회
	public MemberInfoRes getMember(String email) {
		Member member = findMember(email);

		return MemberInfoRes.builder()
			.nickname(member.getNickname())
			.email(member.getEmail())
			.phone(member.getPhone())
			.build();
	}

	// 회원 탈퇴 처리 (논리적 삭제)
	@Transactional
	public void deleteMember(String email) {
		Member member = findMember(email);
		member.delete();  // 논리적 삭제 처리
		memberRepository.save(member);
	}

	public void checkEmail(String email) {
		if (memberRepository.existsByEmail(email)) {
			throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
		}
	}

	// 전화번호 중복 확인
	private void checkPhone(String phone) {
		if (memberRepository.existsByPhone(phone)) {
			throw new IllegalArgumentException("이미 사용 중인 전화번호입니다.");
		}
	}

	private Member findMember(String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
	}
}
