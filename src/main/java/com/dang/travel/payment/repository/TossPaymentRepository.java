package com.dang.travel.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dang.travel.payment.domain.TossPayment;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
}
