package com.dang.travel.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dang.travel.member.domain.Member;
import com.dang.travel.payment.domain.TossPayment;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
	TossPayment findByOrderId(String orderId);

	List<TossPayment> findByMember(Member member);
}
