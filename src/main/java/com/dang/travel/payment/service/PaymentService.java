package com.dang.travel.payment.service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dang.travel.payment.domain.PaymentStatus;
import com.dang.travel.payment.domain.TossPayment;
import com.dang.travel.payment.repository.TossPaymentRepository;
import com.dang.travel.payment.service.dto.request.CreateTosspaymentRequest;
import com.dang.travel.payment.service.dto.response.TossPaymentWidgetResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
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
	@Value("${toss.api.url}")
	private String tossUrl;

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

	@Transactional
	public void tossSuccess(String orderId, String paymentKey, Integer amount) {
		TossPayment tossPayment = tossPaymentRepository.findByOrderId(orderId);
		validTossPayment(tossPayment, paymentKey, amount);
		requestTossPaymentAccept(tossPayment, paymentKey, orderId, amount);
	}

	private void requestTossPaymentAccept(TossPayment tossPayment, String paymentKey, String orderId,
		Integer amount) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = makeTossHeader();
		JSONObject params = new JSONObject();

		params.put("amount", amount);
		params.put("orderId", orderId);
		params.put("paymentKey", paymentKey);

		try {
			ResponseEntity<JsonNode> response = restTemplate.postForEntity(tossUrl + "/v1/payments/confirm",
				new HttpEntity<>(params, headers),
				JsonNode.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				processSuccessfulResponse(response.getBody(), tossPayment);
			}
		} catch (Exception e) {
			log.error("e.getMessage() = " + e.getMessage());
			throw new IllegalArgumentException("토스 결제 승인 요청 중 오류가 발생했습니다.");
		}
	}

	private void processSuccessfulResponse(JsonNode responseBody, TossPayment tossPayment) throws ParseException {
		if (responseBody != null) {
			// ObjectMapper를 사용하여 JsonNode를 Map<String, Object>로 변환
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> receiptMap = objectMapper.convertValue(responseBody, Map.class);

			// receipt 필드에 변환된 Map 저장
			tossPayment.setReceipt(receiptMap);

			// 응답에서 필요한 필드 추출
			String approvedAtStr = responseBody.get("approvedAt").asText();
			String requestedAtStr = responseBody.get("requestedAt").asText();
			String responsePaymentKey = responseBody.get("paymentKey").asText();

			// 날짜 문자열을 Date 객체로 변환
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date approvedAt = formatter.parse(approvedAtStr);
			Date requestedAt = formatter.parse(requestedAtStr);

			// 필드 설정
			tossPayment.setApprovedAt(approvedAt);
			tossPayment.setRequestedAt(requestedAt);
			tossPayment.setPaymentKey(responsePaymentKey);

			// 결제 상태 업데이트
			tossPayment.setPaymentStatus(PaymentStatus.SUCCESS);

			// 엔티티 저장
			tossPaymentRepository.save(tossPayment);

			log.info("Payment approved for orderId: {}", tossPayment.getOrderId());
		}
	}

	private HttpHeaders makeTossHeader() {
		HttpHeaders headers = new HttpHeaders();
		String encodedAuthKey = new String(
			Base64.getEncoder().encode((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8)));
		headers.setBasicAuth(encodedAuthKey);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private void validTossPayment(TossPayment tossPayment, String paymentKey, Integer amount) {
		if (tossPayment == null) {
			throw new IllegalArgumentException("주문 정보가 없습니다.");
		}
		if (tossPayment.getPaymentKey() != null && !tossPayment.getPaymentKey().equals(paymentKey)) {
			throw new IllegalArgumentException("결제 정보가 일치하지 않습니다.");
		}
		if (!tossPayment.getAmount().equals(amount)) {
			throw new IllegalArgumentException("결제 금액이 일치하지 않습니다.");
		}
	}

	public Object getPaymentHistory(String orderId) {
		TossPayment tossPayment = tossPaymentRepository.findByOrderId(orderId);
		if (tossPayment == null) {
			throw new IllegalArgumentException("주문 정보가 없습니다.");
		}
		return tossPayment.getReceipt();
	}

	public Map<String, Object> getPaymentHistories() {
		Map<String, Object> receipts = new HashMap<>();
		List<Map<String, Object>> receiptList = tossPaymentRepository.findAll()
			.stream()
			.map(TossPayment::getReceipt)
			.filter(Objects::nonNull)
			.toList();
		receipts.put("receipts", receiptList);
		return receipts;
	}
}
