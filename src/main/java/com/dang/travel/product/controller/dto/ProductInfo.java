package com.dang.travel.product.controller.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductInfo {
	private Long id;
	private String productName;
	private Long price;
	private LocalDateTime closeTime;
	private Long discountRate;
	private String address;
	private String xCoordinate;
	private String yCoordinate;
	private Long currentPersonnel;
	private Long closePersonnel;
	private String productPicture;
	private String productDetailPicture;
}
