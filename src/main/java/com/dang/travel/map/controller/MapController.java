package com.dang.travel.map.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dang.travel.map.service.MapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/map")
@RequiredArgsConstructor
public class MapController {
	private final MapService mapService;

	@GetMapping("")
	public ResponseEntity<?> getMap(@RequestParam(value = "category", defaultValue = "play") String category) {
		return ResponseEntity.ok(mapService.getMap(category));
	}
}
