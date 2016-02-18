package org.garred.brewdb.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationReceiptPacket extends ReceiptPacket<Location> {

	private static final Class<Location> clazz = Location.class;
	
	@JsonCreator
	public LocationReceiptPacket(
			@JsonProperty("message") String message,
			@JsonProperty("currentPage") int currentPage, 
			@JsonProperty("numberOfPages") int numberOfPages, 
			@JsonProperty("totalResults") int totalResults, 
			@JsonProperty("status") String status, 
			@JsonProperty("data") List<Location> data) {
		super(message, currentPage, numberOfPages, totalResults, status, data);
	}

	@Override
	public Class<Location> resultClass() {
		return clazz;
	}

}
