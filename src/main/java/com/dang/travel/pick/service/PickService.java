package com.dang.travel.pick.service;

import static com.dang.travel.pick.domain.DataGoKrUrl.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dang.travel.pick.domain.Pick;
import com.dang.travel.pick.repository.PickRepository;
import com.dang.travel.pick.service.response.ApiResponse;
import com.dang.travel.pick.service.response.PetItem;
import com.dang.travel.pick.service.response.PickResponse;
import com.dang.travel.pick.service.response.TourItem;
import com.dang.travel.pick.service.response.ToursResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PickService {
	private final PickRepository pickRepository;
	@Value("${data.go.kr.service.key}")
	private String serviceKey;

	public PickResponse getPick(Long id) {
		PetItem petItem = getPetPick(id);
		TourItem tourItem = getTourPick(id);

		if (petItem != null && tourItem != null) {
			PickResponse pickResponse = new PickResponse(tourItem, petItem);
			return pickResponse;
		}
		throw new IllegalArgumentException("No item found.");
	}

	// TourItem용 API 호출 메서드
	public TourItem getTourPick(Long id) {
		String apiUrl = TOUR_URL + "&contentId=" + id + "&serviceKey=" + serviceKey;

		return fetchApiResponse(apiUrl, new ParameterizedTypeReference<>() {
		});
	}

	// PetItem용 API 호출 메서드
	public PetItem getPetPick(Long id) {
		String apiUrl = PET_URL + "&contentId=" + id + "&serviceKey=" + serviceKey;

		return fetchApiResponse(apiUrl, new ParameterizedTypeReference<>() {
		});
	}

	// 공통 API 호출 메서드 (제네릭 사용)
	private <T> T fetchApiResponse(String apiUrl, ParameterizedTypeReference<ApiResponse<T>> responseType) {
		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create(apiUrl);

		try {
			// API 호출
			ResponseEntity<ApiResponse<T>> response = restTemplate.exchange(
				uri,
				org.springframework.http.HttpMethod.GET,
				null,
				responseType
			);

			ApiResponse<T> apiResponse = response.getBody();

			return apiResponse.response().body().items().item().get(0);
		} catch (Exception e) {
			// 에러 처리
			log.error("Error occurred: " + e.getMessage());
			return null;
		}
	}

	public ToursResponse getAllPicks() {
		List<Pick> pickIds = getRandomPicks(pickRepository.findAllBy());
		List<TourItem> tourItems = new ArrayList<>();
		for (Pick pick : pickIds) {
			TourItem tourItem = getTourPick(pick.getId());
			if (tourItem != null) {
				tourItems.add(tourItem);
			}
		}
		return new ToursResponse(tourItems);
	}

	public List<Pick> getRandomPicks(List<Pick> picks) {
		Collections.shuffle(picks);
		return picks.subList(0, 5);
	}
}
