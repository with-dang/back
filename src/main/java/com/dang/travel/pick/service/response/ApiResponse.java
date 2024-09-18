package com.dang.travel.pick.service.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {

	private final Response<T> response;

	@JsonCreator
	public ApiResponse(@JsonProperty("response") Response<T> response) {
		this.response = response;
	}

	public Response<T> response() {
		return response;
	}

	public record Response<T>(Header header, Body<T> body) {
		@JsonCreator
		public Response(
			@JsonProperty("header") Header header,
			@JsonProperty("body") Body<T> body) {
			this.header = header;
			this.body = body;
		}
	}

	public record Header(String resultCode, String resultMsg) {
		@JsonCreator
		public Header(
			@JsonProperty("resultCode") String resultCode,
			@JsonProperty("resultMsg") String resultMsg) {
			this.resultCode = resultCode;
			this.resultMsg = resultMsg;
		}
	}

	public record Body<T>(Items<T> items, int numOfRows, int pageNo, int totalCount) {
		@JsonCreator
		public Body(
			@JsonProperty("items") Items<T> items,
			@JsonProperty("numOfRows") int numOfRows,
			@JsonProperty("pageNo") int pageNo,
			@JsonProperty("totalCount") int totalCount) {
			this.items = items;
			this.numOfRows = numOfRows;
			this.pageNo = pageNo;
			this.totalCount = totalCount;
		}
	}

	public record Items<T>(List<T> item) {
		@JsonCreator
		public Items(@JsonProperty("item") List<T> item) {
			this.item = item;
		}
	}
}
