package com.dang.travel.pick.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ulsan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pick {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
