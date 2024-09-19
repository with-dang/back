package com.dang.travel.pick.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.pick.service.PickService;
import com.dang.travel.pick.service.response.PickResponse;
import com.dang.travel.pick.service.response.ToursResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/pick")
@RequiredArgsConstructor
public class PickController {
	private final PickService pickService;

	@GetMapping("")
	public ResponseEntity<ToursResponse> getAllPicks() {
		return ResponseEntity.ok(pickService.getAllPicks());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PickResponse> getPick(@PathVariable Long id) {
		return ResponseEntity.ok(pickService.getPick(id));
	}
}
