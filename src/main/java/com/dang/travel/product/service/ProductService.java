package com.dang.travel.product.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dang.travel.product.controller.dto.ProductInfo;
import com.dang.travel.product.controller.dto.response.ProductsInfoResponse;
import com.dang.travel.product.domain.Product;
import com.dang.travel.product.repository.ProductRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	@Transactional(readOnly = true)
	public ProductsInfoResponse getProducts() {
		// 삭제되지 않은 제품을 조회
		List<Product> products = productRepository.findByIsDeletedFalseAndCloseTimeAfter(LocalDateTime.now());

		// Product 엔티티를 ProductInfo DTO로 변환
		List<ProductInfo> productInfoList = products.stream()
			.map(product -> ProductInfo.builder()
				.id(product.getId())
				.productName(product.getProductName())
				.price(product.getPrice())
				.closeTime(product.getCloseTime())
				.discountRate(product.getDicountRate())
				.address(product.getAddress())
				.xCoordinate(product.getXCoordinate())
				.yCoordinate(product.getYCoordinate())
				.currentPersonnel(product.getCurrentPersonnel())
				.closePersonnel(product.getClosePersonnel())
				.productPicture(product.getProductPicture())
				.build())
			.collect(Collectors.toList());

		// ProductsInfoResponse로 반환
		return ProductsInfoResponse.builder()
			.products(productInfoList)
			.build();
	}

	@Transactional(readOnly = true)
	public ProductInfo getProductDetail(Long productId) {
		// 삭제되지 않은 제품을 조회
		Product product = productRepository.findByIdAndIsDeletedFalseAndCloseTimeAfter(productId, LocalDateTime.now())
			.orElseThrow(() -> new IllegalArgumentException("해당 제품이 존재하지 않습니다."));

		// Product 엔티티를 ProductInfo DTO로 변환
		return ProductInfo.builder()
			.id(product.getId())
			.productName(product.getProductName())
			.price(product.getPrice())
			.closeTime(product.getCloseTime())
			.discountRate(product.getDicountRate())
			.address(product.getAddress())
			.xCoordinate(product.getXCoordinate())
			.yCoordinate(product.getYCoordinate())
			.currentPersonnel(product.getCurrentPersonnel())
			.closePersonnel(product.getClosePersonnel())
			.productPicture(product.getProductPicture())
			.build();
	}
}
