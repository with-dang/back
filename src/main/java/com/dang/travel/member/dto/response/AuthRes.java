package com.dang.travel.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthRes {

	private String email;
	private String nickname;
	private boolean authenticated;
}
