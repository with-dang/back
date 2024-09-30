package com.dang.travel.product.controller.dto.response;

import java.util.List;

import com.dang.travel.product.controller.dto.ProductInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductsInfoResponse {
	private List<ProductInfo> products;
}
