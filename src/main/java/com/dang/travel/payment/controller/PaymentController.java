package com.dang.travel.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.payment.service.PaymentService;
import com.dang.travel.payment.service.dto.request.CreateTosspaymentRequest;
import com.dang.travel.payment.service.dto.response.TossPaymentWidgetResponse;
import com.dang.travel.security.sessionauth.domain.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
	private final PaymentService paymentService;

	@Operation(summary = "토스 결제 성공", description = "토스 결제 성공시 호출", tags = {"payment"})
	@GetMapping("/toss/success")
	public ResponseEntity<String> tossSuccess(@RequestParam String orderId,
		@RequestParam String paymentKey,
		@RequestParam Integer amount) {
		paymentService.tossSuccess(orderId, paymentKey, amount);
		return ResponseEntity.ok("success");
	}

	@Operation(summary = "토스 결제 생성", description = "토스 결제 하는데 필요한 정보 생성", tags = {"payment"})
	@PostMapping("/toss")
	public ResponseEntity<TossPaymentWidgetResponse> createTossPayment(@RequestBody CreateTosspaymentRequest request,
		@AuthenticationPrincipal CustomUserDetails userDetails) {
		TossPaymentWidgetResponse response = paymentService.createTossPayment(request, userDetails.getUser());
		return ResponseEntity.ok(response);
	}

	//TODO: 영수증 결과 paymentKey 제거 해야함
	@Operation(summary = "결제 영수증 여러개 보여주기", description = "결제 영수증 여러개", tags = {"payment"})
	@GetMapping("/history")
	public ResponseEntity<?> getPaymentHistory(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity.ok(paymentService.getPaymentHistories(userDetails.getUser()));
	}

	@Operation(summary = "결제 영수증 하나 보여주기", description = "결제 영수증 한개", tags = {"payment"})
	@GetMapping("/history/{orderId}")
	public ResponseEntity<?> getPaymentHistory(@PathVariable String orderId) {
		return ResponseEntity.ok(paymentService.getPaymentHistory(orderId));
	}

	@Operation(summary = "결제 내역 상태", description = "수령전 / 공구 확정/ 공구 실패", tags = {"payment"})
	@GetMapping("/status")
	public ResponseEntity<?> getPaymentStatus(@AuthenticationPrincipal CustomUserDetails userDetails) {
		return ResponseEntity.ok(paymentService.getPaymentStatus(userDetails.getUser()));
	}
}
