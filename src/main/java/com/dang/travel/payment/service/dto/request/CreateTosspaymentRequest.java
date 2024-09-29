package com.dang.travel.payment.service.dto.request;

import com.dang.travel.payment.domain.TossPayment;

public record CreateTosspaymentRequest(
	Integer amount
) {
	public TossPayment toEntity(String orderName) {
		return new TossPayment().builder()
			.amount(amount)
			.orderName(orderName)
			.build();
	}
}
