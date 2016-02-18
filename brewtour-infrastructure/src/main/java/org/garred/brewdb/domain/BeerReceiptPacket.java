package org.garred.brewdb.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BeerReceiptPacket extends ReceiptPacket<Beer> {

	private static final Class<Beer> clazz = Beer.class;
	
	@JsonCreator
	public BeerReceiptPacket(
			@JsonProperty("message") String message,
			@JsonProperty("currentPage") int currentPage, 
			@JsonProperty("numberOfPages") int numberOfPages, 
			@JsonProperty("totalResults") int totalResults, 
			@JsonProperty("status") String status, 
			@JsonProperty("data") List<Beer> data) {
		super(message, currentPage, numberOfPages, totalResults, status, data);
	}

	@Override
	public Class<Beer> resultClass() {
		return clazz;
	}

}
