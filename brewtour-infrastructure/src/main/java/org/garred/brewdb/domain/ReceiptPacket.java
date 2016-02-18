package org.garred.brewdb.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public abstract class ReceiptPacket<T> {

	public final String message;
	public final int currentPage;
	public final int numberOfPages;
	public final int totalResults;
	public final String status;
	public final List<T> data;

	@JsonCreator
	public ReceiptPacket(String message, int currentPage, int numberOfPages, int totalResults, String status, List<T> data) {
		this.message = message;
		this.currentPage = currentPage;
		this.numberOfPages = numberOfPages;
		this.totalResults = totalResults;
		this.status = status;
		this.data = data;
	}

	public abstract Class<T> resultClass();

}
