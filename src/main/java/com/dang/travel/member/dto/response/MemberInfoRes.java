package com.dang.travel.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberInfoRes {

	private String nickname;
	private String email;
	private String phone;

}
