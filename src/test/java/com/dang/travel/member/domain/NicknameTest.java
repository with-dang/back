package com.dang.travel.member.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dang.travel.exception.DangtravelException;

class NicknameTest {

	@DisplayName("유효한 닉네임을 생성한다")
	@Test
	void CreateNickname() {
		assertThatNoException().isThrownBy(()-> new Nickname("가ㄱㅏㅣㅎ.AZ1az_"));
	}

	@DisplayName("닉네임의 형식이 잘못되었을 경우 예외를 발생시킨다.")
	@Test
	void cannotCreateNicknameByInvalidFormat() {
		assertThatThrownBy(()-> new Nickname("//"))
			.isInstanceOf(DangtravelException.class)
			.hasMessage("올바르지 않은 닉네임 형식입니다.");

	}

	@DisplayName("닉네임 맨 앞, 뒤 공백은 제거된다.")
	@Test
	void createNicknameAfterTrim() {
		Nickname nickname = new Nickname(" Dang ");

		assertThat(nickname.getNickname()).isEqualTo("Dang");
	}

}
