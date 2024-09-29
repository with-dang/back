package com.dang.travel.payment.service.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TossPaymentWidgetResponse {
	private final String orderId;
	private final String orderName;
	private final String customerEmail;
	private final String customerName;
	private final String successUrl;
	private final String failUrl;
}
