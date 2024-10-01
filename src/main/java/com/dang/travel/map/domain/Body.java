package com.dang.travel.map.domain;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body {
	private int pageIndex;
	private int numOfRows;
	private int pageNo;
	private int totalCount;

	@JacksonXmlElementWrapper(localName = "data")
	@JacksonXmlProperty(localName = "list")
	private List<ListData> data;
}
