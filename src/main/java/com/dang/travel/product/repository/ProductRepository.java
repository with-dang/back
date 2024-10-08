package com.dang.travel.product.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dang.travel.product.domain.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByIsDeletedFalseAndCloseTimeAfter(LocalDateTime currentTime);

	Optional<Product> findByIdAndIsDeletedFalseAndCloseTimeAfter(Long productId, LocalDateTime now);
}
