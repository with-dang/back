package com.dang.travel.pick.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.pick.service.PickService;
import com.dang.travel.pick.service.response.PickResponse;
import com.dang.travel.pick.service.response.ToursResponse;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pick")
@RequiredArgsConstructor
public class PickController {
	private final PickService pickService;

	@Operation(summary = "울산픽 목록 조회", description = "울삭픽을 랜덤 5개 조회", tags = {"pick"})
	@GetMapping("")
	public ResponseEntity<ToursResponse> getAllPicks() {
		return ResponseEntity.ok(pickService.getAllPicks());
	}

	@Operation(summary = "울산픽 상세 조회", description = "울산픽 목록의 번호를 통한 상세 조회", tags = {"pick"})
	@GetMapping("/{id}")
	public ResponseEntity<PickResponse> getPick(@PathVariable Long id) {
		return ResponseEntity.ok(pickService.getPick(id));
	}
}
