package com.dang.travel.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.payment.service.PaymentService;
import com.dang.travel.payment.service.dto.request.CreateTosspaymentRequest;
import com.dang.travel.payment.service.dto.response.TossPaymentWidgetResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@Operation(summary = "토스 결제 생성", description = "토스 결제 하는데 필요한 정보 생성", tags = {"payment"})
	@PostMapping("/toss")
	public ResponseEntity<TossPaymentWidgetResponse> createTossPayment(@RequestBody CreateTosspaymentRequest request) {
		TossPaymentWidgetResponse response = paymentService.createTossPayment(request);
		return ResponseEntity.ok(response);
	}
}
