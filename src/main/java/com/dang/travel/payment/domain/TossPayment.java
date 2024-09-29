package com.dang.travel.payment.domain;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`order`")
@NoArgsConstructor
public class TossPayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private String paymentKey;
	@Column(nullable = false)
	private Integer amount;
	@Column(nullable = false, unique = true)
	private String orderId;
	@Column(nullable = false)
	private String orderName;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	@Column(nullable = true)
	private Date requestedAt;
	@Column(nullable = true)
	private Date approvedAt;
	@Column(columnDefinition = "JSON")
	private String receipt;

	@Builder
	public TossPayment(Integer amount, String orderName) {
		this.orderId = UUID.randomUUID().toString();
		this.paymentStatus = PaymentStatus.PENDING;
		this.amount = amount;
		this.orderName = orderName;
	}
}
