package com.dang.travel.payment.service.dto.request;

import com.dang.travel.member.domain.Member;
import com.dang.travel.payment.domain.TossPayment;

public record CreateTosspaymentRequest(
	Integer amount
) {
	public TossPayment toEntity(String orderName, Member member) {
		return new TossPayment().builder()
			.member(member)
			.amount(amount)
			.orderName(orderName)
			.build();
	}
}
