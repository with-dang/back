package com.dang.travel.product.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.dang.travel.config.domain.BaseEntity;
import com.dang.travel.member.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE id = ?")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SQLRestriction("is_deleted = false")
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String productName;

	@Column
	private Long price;

	@Column
	private LocalDateTime closeTime;

	@Column
	private String address;

	@Column
	private String xCoordinate;

	@Column
	private String yCoordinate;

	@Column(nullable = false, columnDefinition = "bigint default 0")
	private Long currentPersonnel;

	@Column
	private Long closePersonnel;

	@Column
	private Long dicountRate;

	@Column
	private String productPicture;

	private Boolean isDeleted = false;

}
