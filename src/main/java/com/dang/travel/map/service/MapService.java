package com.dang.travel.map.service;

import static com.dang.travel.pick.domain.DataGoKrUrl.*;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dang.travel.map.domain.RfcOpenApi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapService {
	@Value("${data.go.kr.service.key}")
	private String serviceKey;

	public List<?> getMap(String category) {
		switch (category.toLowerCase()) {
			case "stuff":
				return getAdress(STUFF_URL).getBody().getData();
			case "animal":
				return getAdress(ANIMAL_URL).getBody().getData();
			default:
				return getAdress(PLAY_URL).getBody().getData();
		}
	}

	private RfcOpenApi getAdress(String url) {
		String apiUrl = url + "&serviceKey=" + serviceKey;
		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(apiUrl);
		RfcOpenApi rfcOpenApi = restTemplate.getForObject(uri, RfcOpenApi.class);
		return rfcOpenApi;
	}
}
