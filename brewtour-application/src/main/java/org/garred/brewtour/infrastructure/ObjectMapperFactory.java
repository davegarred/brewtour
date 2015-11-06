package org.garred.brewtour.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

	public static ObjectMapper objectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
