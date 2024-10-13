package com.dang.travel.member.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dang.travel.config.domain.BaseEntity;
import com.dang.travel.payment.domain.TossPayment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id = ?")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SQLRestriction("is_deleted = false")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, length = 128)
	private String password;

	@Column
	private Role role;

	@Column
	private String phone;

	@OneToMany(mappedBy = "member")
	private List<TossPayment> tossPayments = new ArrayList<>();
	private Boolean isDeleted = false;

	// 빌더 패턴을 적용한 생성자
	@Builder
	public Member(String nickname, String email, String password, Role role, String phone) {
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.role = role;
		this.phone = phone;
	}

	// 회원 탈퇴(삭제) 처리
	public void delete() {
		this.isDeleted = true;
	}

}
