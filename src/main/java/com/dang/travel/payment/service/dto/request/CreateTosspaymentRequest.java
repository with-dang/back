package com.dang.travel.payment.service.dto.request;

import com.dang.travel.member.domain.Member;
import com.dang.travel.payment.domain.TossPayment;
import com.dang.travel.product.domain.Product;

public record CreateTosspaymentRequest(
	Integer amount,
	Long productId
) {
	public TossPayment toEntity(String orderName, Member member, Product product) {
		return new TossPayment().builder()
			.product(product)
			.member(member)
			.amount(amount)
			.orderName(orderName)
			.build();
	}
}
