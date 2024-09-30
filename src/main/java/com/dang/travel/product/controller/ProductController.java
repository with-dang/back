package com.dang.travel.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.product.controller.dto.response.ProductsInfoResponse;
import com.dang.travel.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@GetMapping("/productsInfo")
	public ResponseEntity<ProductsInfoResponse> getProducts() {
		ProductsInfoResponse response = productService.getProducts();
		return ResponseEntity.ok(response);
	}


}
