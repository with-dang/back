package com.dang.travel.payment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dang.travel.payment.domain.TossPayment;
import com.dang.travel.payment.repository.TossPaymentRepository;
import com.dang.travel.payment.service.dto.request.CreateTosspaymentRequest;
import com.dang.travel.payment.service.dto.response.TossPaymentWidgetResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
	private final TossPaymentRepository tossPaymentRepository;
	@Value("${toss.api.secret.key}")
	private String tossSecretKey;
	@Value("${toss.api.success.url}")
	private String tossSuccessUrl;
	@Value("${toss.api.fail.url}")
	private String tossFailUrl;

	// TODO: 유저 연동시 변경 필요
	public TossPaymentWidgetResponse createTossPayment(CreateTosspaymentRequest request) {
		TossPayment tossPayment = request.toEntity("테스트 주문");
		tossPaymentRepository.save(tossPayment);
		return TossPaymentWidgetResponse.builder()
			.orderId(tossPayment.getOrderId())
			.orderName("테스트 주문")
			.customerEmail("test@email.com")
			.customerName("익명 이름")
			.successUrl(tossSuccessUrl)
			.failUrl(tossFailUrl)
			.build();
	}
}
